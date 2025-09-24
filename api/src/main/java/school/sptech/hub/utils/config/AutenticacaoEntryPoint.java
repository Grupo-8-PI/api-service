package school.sptech.hub.utils.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AutenticacaoEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AutenticacaoEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        int status;
        String mensagem;

        if (authException instanceof BadCredentialsException){
            status = HttpServletResponse.SC_UNAUTHORIZED;
            mensagem = "Credenciais inválidas. Verifique seu email e senha.";
            logger.warn("Tentativa de login falhou: credenciais inválidas. IP: {}", request.getRemoteAddr());
        } else if (authException instanceof InsufficientAuthenticationException) {
            status = HttpServletResponse.SC_UNAUTHORIZED;
            mensagem = "Autenticação insuficiente. Token ausente ou inválido.";
            logger.warn("Tentativa de acesso com autenticação insuficiente. IP: {}", request.getRemoteAddr());
        } else {
            status = HttpServletResponse.SC_FORBIDDEN;
            mensagem = "Acesso proibido. Você não tem permissão para acessar este recurso." + authException.getMessage();
            logger.warn("Acesso proibido detectado. IP: {}, Motivo: {}", request.getRemoteAddr(), authException.getMessage());
        }

        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(String.format("{\"error\": \"%s\", \"status\": %d}", mensagem, status));
    }
}