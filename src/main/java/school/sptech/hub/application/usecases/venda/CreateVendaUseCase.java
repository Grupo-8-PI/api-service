package school.sptech.hub.application.usecases.venda;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.VendaExceptions.VendaInvalidaException;
import school.sptech.hub.application.exceptions.VendaExceptions.VendaNaoEncontradaException;
import school.sptech.hub.application.gateways.venda.VendaGateway;
import school.sptech.hub.domain.entity.Venda;

import static school.sptech.hub.application.validators.VendaValidator.isValidVenda;

@Component
public class CreateVendaUseCase {

    private final VendaGateway gateway;

    public CreateVendaUseCase(VendaGateway gateway) {
        this.gateway = gateway;
    }

    public Venda execute(Venda venda) {
        boolean isReservaValid = isValidVenda(venda);
        if (!isReservaValid) {
            throw new VendaInvalidaException("Reserva inválida.");
        }
        
        return gateway.createVenda(venda)
                .orElseThrow(() -> new VendaNaoEncontradaException("Erro ao criar reserva"));
    }
}
