package school.sptech.hub.application.usecases.venda;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.VendaExceptions.VendaNaoEncontradaException;
import school.sptech.hub.application.gateways.venda.VendaGateway;
import school.sptech.hub.domain.entity.Venda;

@Component
public class DeleteVendaUseCase {

    private final VendaGateway gateway;

    public DeleteVendaUseCase(VendaGateway gateway) {
        this.gateway = gateway;
    }

    public Venda execute(Integer id) {
        Venda vendaFinded = gateway.findById(id)
                .orElseThrow(() -> new VendaNaoEncontradaException("Reserva não encontrada"));
        
        gateway.deleteVenda(vendaFinded);
        return vendaFinded;
    }
}
