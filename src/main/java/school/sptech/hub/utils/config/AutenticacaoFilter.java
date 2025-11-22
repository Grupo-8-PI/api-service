package school.sptech.hub.utils.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import school.sptech.hub.application.service.*;

import java.io.IOException;
import java.util.*;

public class AutenticacaoFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoFilter.class);

    private final AutenticacaoService autenticacaoService;
    private final GerenciadorTokenJwt jwtTokenManager;

    public AutenticacaoFilter(AutenticacaoService autenticacaoService, GerenciadorTokenJwt jwtTokenManager) {
        this.autenticacaoService = autenticacaoService;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestTokenHeader = request.getHeader("Authorization");

        if (Objects.isNull(requestTokenHeader) || !requestTokenHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = requestTokenHeader.substring(7);
        String username;

        try {
            username = jwtTokenManager.getUsernameFromToken(jwtToken);

            if (username == null || username.isBlank()) {
                throw new IllegalArgumentException("Token não contém um username válido.");
            }

            if (!jwtTokenManager.validarToken(jwtToken, autenticacaoService.loadUserByUsername(username))) {
                throw new SignatureException("Token inválido ou adulterado.");
            }

        } catch (ExpiredJwtException e) {
            LOGGER.warn("[FALHA AUTENTICAÇÃO] Token expirado. Usuário: {} - IP: {}",
                    e.getClaims().getSubject(), request.getRemoteAddr());
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token expirado. Faça login novamente.");
            return;

        } catch (SignatureException | MalformedJwtException e) {
            LOGGER.warn("[FALHA AUTENTICAÇÃO] Token inválido detectado. IP: {} - Erro: {}",
                    request.getRemoteAddr(), e.getMessage());
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou adulterado.");
            return;

        } catch (Exception e) {
            LOGGER.error("[ERRO INTERNO] Durante a validação do token. IP: {} - Erro: {}",
                    request.getRemoteAddr(), e.getMessage());
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno de autenticação.");
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            autenticarUsuarioNoContexto(request, jwtToken, username);
        }

        filterChain.doFilter(request, response);
    }

    private void autenticarUsuarioNoContexto(HttpServletRequest request, String jwtToken, String username) {
        UserDetails userDetails = autenticacaoService.loadUserByUsername(username);

        String rolesClaim = jwtTokenManager.getClaimForToken(jwtToken, claims -> claims.get("roles", String.class));

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (rolesClaim != null && !rolesClaim.isBlank()) {
            Arrays.stream(rolesClaim.split(","))
                    .map(String::trim)
                    .filter(role -> !role.isEmpty())
                    .forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        LOGGER.info("Usuário '{}' autenticado com sucesso. IP: {}", username, request.getRemoteAddr());
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
                String.format("{\"error\": \"%s\", \"status\": %d}", message, status)
        );
    }
}