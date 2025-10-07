package school.sptech.hub.application.usecases.venda;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.usuario.UsuarioGateway;
import school.sptech.hub.application.gateways.venda.VendaGateway;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.domain.entity.Venda;

import java.util.List;

@Component
public class ListAllVendasByClienteUseCase {

    private final VendaGateway vendaGateway;
    private final UsuarioGateway usuarioGateway;

    public ListAllVendasByClienteUseCase(VendaGateway vendaGateway, UsuarioGateway usuarioGateway) {
        this.vendaGateway = vendaGateway;
        this.usuarioGateway = usuarioGateway;
    }

    public List<Venda> execute(Integer clienteId) {
        Usuario cliente = usuarioGateway.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + clienteId));
        return vendaGateway.findVendasByClienteId(clienteId);
    }
}
