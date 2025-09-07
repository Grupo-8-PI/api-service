package school.sptech.hub.application.usecases.usuario;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.hub.application.gateways.usuario.UsuarioGateway;
import school.sptech.hub.domain.dto.usuario.UsuarioTokenDto;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.utils.config.GerenciadorTokenJwt;
import school.sptech.hub.domain.dto.usuario.UsuarioMapper;

@Service
public class AuthenticateUsuarioUseCase {
    private final UsuarioGateway gateway;
    private final AuthenticationManager authenticationManager;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;

    public AuthenticateUsuarioUseCase(UsuarioGateway gateway,
                                      AuthenticationManager authenticationManager,
                                      GerenciadorTokenJwt gerenciadorTokenJwt) {
        this.gateway = gateway;
        this.authenticationManager = authenticationManager;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
    }

    public UsuarioTokenDto execute(Usuario usuario) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuario.getEmail(), usuario.getSenha()
        );

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =
                gateway.findByEmail(usuario.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email de usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.toUsuarioTokenDto(usuarioAutenticado, token);
    }
}
