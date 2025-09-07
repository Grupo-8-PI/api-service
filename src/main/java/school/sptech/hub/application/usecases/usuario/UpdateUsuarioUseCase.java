package school.sptech.hub.application.usecases.usuario;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.application.gateways.usuario.UsuarioGateway;
import school.sptech.hub.domain.dto.usuario.UsuarioMapper;
import school.sptech.hub.domain.dto.usuario.UsuarioResponseDto;
import school.sptech.hub.domain.dto.usuario.UsuarioUpdateTokenDto;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.utils.config.GerenciadorTokenJwt;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Component
public class UpdateUsuarioUseCase {

    private final UsuarioGateway gateway;
    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;

    public UpdateUsuarioUseCase(UsuarioGateway gateway, PasswordEncoder passwordEncoder, GerenciadorTokenJwt gerenciadorTokenJwt) {
        this.gateway = gateway;
        this.passwordEncoder = passwordEncoder;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
    }

    public UsuarioUpdateTokenDto execute(Integer id, Usuario usuario) {
        Usuario existingUser = gateway.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        Usuario updatedUser = UsuarioMapper.updateUserFields(existingUser, usuario);
        UsuarioResponseDto usuarioResponseDto = UsuarioMapper.toResponseDto(updatedUser);

        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        updatedUser.setSenha(senhaCriptografada);

        UsuarioDetalhesDto usuarioDetalhes = new UsuarioDetalhesDto(updatedUser);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(usuarioDetalhes, null, usuarioDetalhes.getAuthorities());

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        gateway.updateUsuario(updatedUser);

        return UsuarioMapper.toUsuarioUpdateDto(usuarioResponseDto, token);
    }
}
