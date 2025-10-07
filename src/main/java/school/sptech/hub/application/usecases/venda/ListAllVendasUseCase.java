package school.sptech.hub.application.usecases.venda;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.venda.VendaGateway;
import school.sptech.hub.domain.entity.Venda;

import java.util.List;

@Component
public class ListAllVendasUseCase {

    private final VendaGateway vendaGateway;

    public ListAllVendasUseCase(VendaGateway vendaGateway) {
        this.vendaGateway = vendaGateway;
    }

    public List<Venda> execute() {
        return vendaGateway.findAll();
    }
}
