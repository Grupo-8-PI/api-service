package school.sptech.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.hub.entity.Venda;
import school.sptech.hub.exceptions.VendaExceptions.VendaInvalidaException;
import school.sptech.hub.exceptions.VendaExceptions.VendaNaoEncontradaException;
import school.sptech.hub.repository.ReservaRepository;

@Service
public class VendaService {

    @Autowired
    private ReservaRepository repository;

    public Venda createReserva(Venda venda) {
        if(venda.getTotalReserva() <= 0) {
            throw new VendaInvalidaException("Reserva inválida.");
        }
        //TODO
        //MANY TO ONE IMPLEMENTAION
        return repository.save(venda);
    }

    public Venda updateReserva(Integer id, Venda vendaToBeUpdated){
        if(vendaToBeUpdated.getTotalReserva() <= 0|| vendaToBeUpdated.getTotalReserva() == null){
            throw new VendaInvalidaException("Reserva inválida.");
        }
        vendaToBeUpdated.setId(id);

        return repository.save(vendaToBeUpdated);

    }
    public Venda getReservaById(Integer id){
        Venda venda = repository.findById(id).orElseThrow(()-> new VendaNaoEncontradaException("Reserva não encontrada"));
        return venda;
    }
    public Venda deleteReservaById(Integer id){
        Venda vendaFinded = repository.findById(id).orElseThrow(()-> new VendaNaoEncontradaException("Reserva não encontrada"));
        repository.deleteById(id);
        return vendaFinded;
    }
}
