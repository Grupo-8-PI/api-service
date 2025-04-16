package school.sptech.hub.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.hub.entity.Venda;
import school.sptech.hub.service.VendaService;

@RequestMapping("/reservas")
@RestController
public class VendaController {

    @Autowired
    private VendaService service;

    @PostMapping
    public ResponseEntity<Venda> createReserva(@RequestBody Venda venda) {
        Venda createdVenda = service.createReserva(venda);
        if(createdVenda != null) {
            return ResponseEntity.status(200).body(createdVenda);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venda> updateReservaById(@PathVariable Integer id, @RequestBody Venda vendaToUpdate){
        Venda updatedVenda = service.updateReserva(id, vendaToUpdate);
        if(updatedVenda ==null){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(updatedVenda);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> getReservaById(@PathVariable Integer id){
        Venda venda = service.getReservaById(id);
        if(venda == null){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(venda);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Venda> deleteReservaById(@PathVariable Integer id){
        Venda venda = service.deleteReservaById(id);
        if(venda == null){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(venda);
    }

}
