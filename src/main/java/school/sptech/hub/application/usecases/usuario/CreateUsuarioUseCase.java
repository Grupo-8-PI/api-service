package school.sptech.hub.application.usecases.usuario;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.application.gateways.usuario.UsuarioGateway;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.application.exceptions.UsuarioExceptions.TipoUsuarioInvalidoException;
import school.sptech.hub.domain.dto.usuario.UsuarioCreateDto;
import school.sptech.hub.domain.dto.usuario.UsuarioMapper;
import school.sptech.hub.domain.dto.usuario.UsuarioResponseDto;

import java.util.Optional;

import static school.sptech.hub.application.validators.UsuarioValidator.isValidUserType;

@Component
public class CreateUsuarioUseCase {

    private final UsuarioGateway gateway;
    private final PasswordEncoder passwordEncoder;

    public CreateUsuarioUseCase(UsuarioGateway gateway, PasswordEncoder passwordEncoder) {
        this.gateway = gateway;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponseDto execute(UsuarioCreateDto usuario) {
        if (!isValidUserType(usuario.getTipo_usuario())) {
            throw new TipoUsuarioInvalidoException("Tipo de usuário inválido.");
        }

        // Verificar se email já existe
        if (gateway.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Usuario usuarioEntity = UsuarioMapper.toEntity(usuario);

        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuarioEntity.setSenha(senhaCriptografada);

        Optional<Usuario> createdUser = gateway.createUsuario(usuarioEntity);
        Usuario usuarioSalvo = createdUser.orElseThrow(() -> new UsuarioNaoEncontradoException("Erro ao criar usuário"));
        return UsuarioMapper.toResponseDto(usuarioSalvo);
    }
}