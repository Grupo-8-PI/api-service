package school.sptech.hub.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.hub.entity.Reserva;
import school.sptech.hub.service.ReservaService;

@RestController
public class ReservaController {

    @Autowired
    private ReservaService service;

    @PostMapping
    public ResponseEntity<Reserva> createReserva(@RequestBody Reserva reserva) {
        Reserva createdReserva = service.createReserva(reserva);
        if(createdReserva != null) {
            return ResponseEntity.status(200).body(createdReserva);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @PutMapping("/id")
    public ResponseEntity<Reserva> updateReservaById(@PathVariable Integer id, @RequestBody Reserva reservaToUpdate){
        Reserva updatedReserva = service.updateReserva(id, reservaToUpdate);
        if(updatedReserva ==null){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(updatedReserva);
    }

}
