package school.sptech.hub.application.usecases.usuario;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.application.gateways.usuario.UsuarioGateway;
import school.sptech.hub.domain.dto.usuario.UsuarioMapper;
import school.sptech.hub.domain.dto.usuario.UsuarioResponseDto;
import school.sptech.hub.domain.entity.Usuario;

@Component
public class GetUsuarioByIdUseCase {

    private final UsuarioGateway gateway;

    public GetUsuarioByIdUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public UsuarioResponseDto execute(Integer id) {
        Usuario usuarioFinded = gateway.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        return UsuarioMapper.toResponseDto(usuarioFinded);
    }
}
