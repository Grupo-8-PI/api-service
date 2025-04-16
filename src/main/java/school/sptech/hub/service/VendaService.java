package school.sptech.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.hub.entity.Venda;
import school.sptech.hub.repository.ReservaRepository;

import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private ReservaRepository repository;

    public Venda createReserva(Venda venda) {
        if(venda.getTotalReserva() <= 0) {
            return null; // Invalid reservation
        }
        //TODO
        //MANY TO ONE IMPLEMENTAION
        return repository.save(venda);
    }

    public Venda updateReserva(Integer id, Venda vendaToBeUpdated){
        if(vendaToBeUpdated.getTotalReserva() <= 0|| vendaToBeUpdated.getTotalReserva() == null){
            return null;
        }
        vendaToBeUpdated.setId(id);
        Optional<Venda> reservaById = repository.findById(id);
        if(reservaById.isPresent()){
            return repository.save(vendaToBeUpdated);
        }
            return null;
    }
    public Venda getReservaById(Integer id){
        Venda venda = repository.findById(id).orElse(null);
        return venda;
    }
    public Venda deleteReservaById(Integer id){
        Venda vendaFinded = repository.findById(id).orElse(null);
        if(vendaFinded == null){
            return null;
        }
        repository.deleteById(id);
        return vendaFinded;
    }
}
