package school.sptech.demo.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.demo.crud.entity.Doador;
import school.sptech.demo.crud.service.DoadorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doador")
public class DoadorController {
@Autowired
DoadorService service;

@GetMapping
    public ResponseEntity<List<Doador>> getDoador(){
    List<Doador> doadoresAchados = service.getAllDoadores();
    if(doadoresAchados.isEmpty()){
        return ResponseEntity.status(204).build();
    }
    return ResponseEntity.status(200).body(doadoresAchados);
}
@GetMapping("/name_search")
    public ResponseEntity<?> getDoadorByNome(@RequestParam String nome){
    List<Doador> doadorWithTheSameName = service.getDoadorByNome(nome);
    if(doadorWithTheSameName.size()==0){
        return ResponseEntity.status(404).build();
    }else if(doadorWithTheSameName.size()>1){
        return ResponseEntity.status(409).body("Error: there were more than 1 person with the same name");
    }else{
        return ResponseEntity.status(200).body(doadorWithTheSameName.getFirst());
    }
}

@GetMapping("/{id}")
    public ResponseEntity<Doador> getDoadorById(@PathVariable Integer id){
    Optional<Doador> doadorReceived = service.getDoadorById(id);
    if (doadorReceived.isPresent()) {
        Doador doador = doadorReceived.get();
        return ResponseEntity.status(200).body(doador);
    }
    return ResponseEntity.status(404).build();
}
@PostMapping()
    public ResponseEntity<?> postDoador(@RequestBody Doador doador){
    try{
        Doador createdDoador = service.postDoador(doador);
        return ResponseEntity.status(201).body(createdDoador);
    }catch (DataIntegrityViolationException conflict){
        return ResponseEntity.status(409).body("There was a conflict, the CPF presented is already posted");
    }
}
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoadorById(@PathVariable Integer id,@RequestBody Doador doador){
        Doador doadorToBeUpdated = service.putDoadorById(id,doador);
        if(doadorToBeUpdated==null){
            return ResponseEntity.status(404).body("Cannot find doador with the Id requested");
        }else{
            return ResponseEntity.status(200).body(doadorToBeUpdated);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoadorById(@PathVariable Integer id){
        Boolean isDeleted = service.deleteDoadorById(id);
        if(isDeleted){
            return ResponseEntity.status(200).build();
        }
            return ResponseEntity.status(404).body("Cannot find doador with the Id requested");
    }

}
