package school.sptech.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.hub.entity.Venda;
import school.sptech.hub.exceptions.VendaExceptions.VendaInvalidaException;
import school.sptech.hub.exceptions.VendaExceptions.VendaNaoEncontradaException;
import school.sptech.hub.repository.ReservaRepository;

import java.util.Optional;

import static school.sptech.hub.validators.VendaValidator.isValidVenda;

@Service
public class VendaService {

    @Autowired
    private ReservaRepository repository;

    public Venda createReserva(Venda venda) {
        boolean isReservaValid = isValidVenda(venda);
        if (!isReservaValid) {
            throw new VendaInvalidaException("Reserva inválida.");
        }
        return repository.save(venda);
    }

    public Venda updateReserva(Integer id, Venda vendaToBeUpdated) {
        boolean isReservaValid = isValidVenda(vendaToBeUpdated);
        if (!isReservaValid) {
            throw new VendaInvalidaException("Reserva inválida.");
        }
        vendaToBeUpdated.setId(id);

        return repository.save(vendaToBeUpdated);

    }

    public Venda getReservaById(Integer id) {
        Venda venda = repository.findById(id).orElseThrow(() -> new VendaNaoEncontradaException("Reserva não encontrada"));
        return venda;
    }

    public Venda deleteReservaById(Integer id) {
        Venda vendaFinded = repository.findById(id).orElseThrow(() -> new VendaNaoEncontradaException("Reserva não encontrada"));
        repository.deleteById(id);
        return vendaFinded;
    }

    public boolean reservaPertenceAoUsuario(Integer idReserva, String emailUsuario) {
        Optional<Venda> vendaOpt = repository.findById(idReserva);
        return vendaOpt.isPresent() && vendaOpt.get().getUsuarios().getEmail().equals(emailUsuario);
    }
}