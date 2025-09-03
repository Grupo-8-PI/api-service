package school.sptech.hub.application.usecases.venda;

import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.application.exceptions.VendaExceptions.VendaInvalidaException;
import school.sptech.hub.infraestructure.persistance.ReservaRepository;

import static school.sptech.hub.application.validators.VendaValidator.isValidVenda;

public class UpdateVendaReservaUseCase {
    private final ReservaRepository repository;

    public UpdateVendaReservaUseCase(ReservaRepository repository) {
        this.repository = repository;
    }

    public Venda execute(Integer id, Venda vendaToBeUpdated) {
        boolean isReservaValid = isValidVenda(vendaToBeUpdated);
        if (!isReservaValid) {
            throw new VendaInvalidaException("Reserva inválida.");
        }
        vendaToBeUpdated.setId(id);
        return repository.save(vendaToBeUpdated);
    }
}

