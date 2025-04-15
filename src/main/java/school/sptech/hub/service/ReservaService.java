package school.sptech.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.hub.entity.Reserva;
import school.sptech.hub.repository.ReservaRepository;

import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    public Reserva createReserva(Reserva reserva) {
        if(reserva.getTotalReserva() <= 0) {
            return null; // Invalid reservation
        }
        //TODO
        //MANY TO ONE IMPLEMENTAION
        return repository.save(reserva);
    }

    public Reserva updateReserva(Integer id, Reserva reservaToBeUpdated) throws IllegalArgumentException{
        if(reservaToBeUpdated.getTotalReserva() <= 0||reservaToBeUpdated.getTotalReserva() == null){
            return null;
        }
        reservaToBeUpdated.setId(id);
        Optional<Reserva> reservaById = repository.findById(id);
        if(reservaById.isPresent()){
            return repository.save(reservaToBeUpdated);
        }
            return null;
    }

}
