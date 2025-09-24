package school.sptech.hub.application.usecases.venda;

import org.springframework.stereotype.Component;
import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.application.exceptions.VendaExceptions.VendaInvalidaException;
import school.sptech.hub.application.exceptions.VendaExceptions.VendaNaoEncontradaException;
import school.sptech.hub.application.gateways.venda.VendaGateway;

import static school.sptech.hub.application.validators.VendaValidator.isValidVenda;

@Component
public class UpdateVendaReservaUseCase {
    private final VendaGateway gateway;

    public UpdateVendaReservaUseCase(VendaGateway gateway) {
        this.gateway = gateway;
    }

    public Venda execute(Integer id, Venda vendaToBeUpdated) {
        boolean isReservaValid = isValidVenda(vendaToBeUpdated);
        if (!isReservaValid) {
            throw new VendaInvalidaException("Reserva inválida.");
        }
        vendaToBeUpdated.setId(id);

        return gateway.updateVenda(vendaToBeUpdated)
                .orElseThrow(() -> new VendaNaoEncontradaException("Erro ao atualizar reserva"));
    }
}
