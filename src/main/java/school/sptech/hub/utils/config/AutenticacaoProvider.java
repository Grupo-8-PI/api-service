package school.sptech.hub.utils.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import school.sptech.hub.application.service.*;

public class AutenticacaoProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoProvider.class);

    private final AutenticacaoService usuarioAutenticacaoService;
    private final PasswordEncoder passwordEncoder;

    public AutenticacaoProvider(AutenticacaoService usuarioAutenticacaoService, PasswordEncoder passwordEncoder) {
        this.usuarioAutenticacaoService = usuarioAutenticacaoService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials() != null ? authentication.getCredentials().toString() : "";

        // Validação básica para evitar erros e ataques
        if (username == null || username.isBlank() || password.isBlank()) {
            LOGGER.warn("Tentativa de login inválida - campos vazios. Username: {}", username);
            throw new BadCredentialsException("Credenciais inválidas.");
        }

        LOGGER.info("Tentativa de autenticação para o usuário: {}", username);

        UserDetails userDetails = this.usuarioAutenticacaoService.loadUserByUsername(username);

        // Verificação segura da senha
        if (this.passwordEncoder.matches(password, userDetails.getPassword())) {
            LOGGER.info("Autenticação bem-sucedida para o usuário: {}", username);
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } else {
            LOGGER.warn("Falha de autenticação para o usuário: {}", username);
            throw new BadCredentialsException("Credenciais inválidas.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}