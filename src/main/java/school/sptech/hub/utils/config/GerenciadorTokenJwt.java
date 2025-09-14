package school.sptech.hub.utils.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import school.sptech.hub.domain.dto.usuario.UsuarioDetalhesDto;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
public class GerenciadorTokenJwt {

    private static final Logger LOGGER = LoggerFactory.getLogger(GerenciadorTokenJwt.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.validity}")
    private long jwtTokenValidity;

    public String getUsernameFromToken(String token) {
        return getClaimForToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimForToken(token, Claims::getExpiration);
    }

    public String generateToken(final Authentication authentication) {


        UsuarioDetalhesDto usuarioDetalhes = (UsuarioDetalhesDto) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(usuarioDetalhes.getUsername())
                .claim("id", usuarioDetalhes.getId())
                .claim("roles", "ROLE_" + usuarioDetalhes.getTipoUsuario())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity))
                .signWith(parseSecret(), SignatureAlgorithm.HS512)
                .compact();
    }

    public <T> T getClaimForToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public boolean validarToken(String token, UserDetails userDetails) {
        try{
            String username = getUsernameFromToken(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (ExpiredJwtException e){
            LOGGER.warn("Token expirado: {}", e.getMessage());
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e){
            LOGGER.error("Token inválido: {}", e.getMessage());
        }

        return false;
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date(System.currentTimeMillis()));
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(parseSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey parseSecret() {
        return Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
    }

}