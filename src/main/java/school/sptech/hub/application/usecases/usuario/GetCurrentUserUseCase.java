package school.sptech.hub.application.usecases.usuario;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.application.gateways.usuario.UsuarioGateway;
import school.sptech.hub.domain.dto.usuario.UsuarioMapper;
import school.sptech.hub.domain.dto.usuario.UsuarioResponseDto;
import school.sptech.hub.domain.entity.Usuario;

@Component
public class GetCurrentUserUseCase {

    private final UsuarioGateway gateway;

    public GetCurrentUserUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public UsuarioResponseDto execute(Integer userId) {
        Usuario usuario = gateway.findById(userId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        return UsuarioMapper.toResponseDto(usuario);
    }
}

