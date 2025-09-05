package school.sptech.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.hub.entity.Venda;
import school.sptech.hub.exceptions.VendaExceptions.VendaInvalidaException;
import school.sptech.hub.exceptions.VendaExceptions.VendaNaoEncontradaException;
import school.sptech.hub.repository.ReservaRepository;

import java.util.Optional;

import static school.sptech.hub.validators.VendaValidator.isValidVenda;

@Service
public class VendaService {

    @Autowired
    ReservaRepository repository;

    @Transactional
    public Venda createReserva(Venda venda) {

        if (venda == null || !isValidVenda(venda)) {
            throw new VendaInvalidaException("Reserva inválida.");
        }

        return repository.save(venda);
    }

    @Transactional
    public Venda updateReserva(Integer id, Venda vendaToBeUpdated) {

        if (id == null || id <= 0 || vendaToBeUpdated == null || !isValidVenda(vendaToBeUpdated)) {
            throw new VendaInvalidaException("Reserva inválida.");
        }

        repository.findById(id)
                .orElseThrow(() -> new VendaNaoEncontradaException("Reserva não encontrada"));

        vendaToBeUpdated.setId(id);

        return repository.save(vendaToBeUpdated);
    }

    @Transactional(readOnly = true)
    public Venda getReservaById(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        return repository.findById(id)
                .orElseThrow(() -> new VendaNaoEncontradaException("Reserva não encontrada"));

    }

    @Transactional
    public Venda deleteReservaById(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        Venda vendaFinded = repository.findById(id)
                .orElseThrow(() -> new VendaNaoEncontradaException("Reserva não encontrada"));

        repository.deleteById(id);

        vendaFinded.setUsuarios(null);
        return vendaFinded;
    }

    @Transactional(readOnly = true)
    public boolean reservaPertenceAoUsuario(Integer idReserva, String emailUsuario) {

        if (idReserva == null || idReserva <= 0 || emailUsuario == null || emailUsuario.isBlank()) {
            return false;
        }

        Optional<Venda> vendaOpt = repository.findById(idReserva);

       return vendaOpt.isPresent() &&
               vendaOpt.get().getUsuarios() != null &&
               emailUsuario.equalsIgnoreCase(vendaOpt.get().getUsuarios().getEmail());
    }
}