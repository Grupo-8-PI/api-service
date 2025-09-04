package school.sptech.hub.application.usecases.usuario;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.application.gateways.usuario.UsuarioGateway;
import school.sptech.hub.domain.entity.Usuario;

@Component
public class DeleteUsuarioUseCase {

    private final UsuarioGateway gateway;

    public DeleteUsuarioUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public Usuario execute(Integer id) {
        Usuario usuarioFinded = gateway.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        
        gateway.deleteUsuario(usuarioFinded);
        return usuarioFinded;
    }
}
