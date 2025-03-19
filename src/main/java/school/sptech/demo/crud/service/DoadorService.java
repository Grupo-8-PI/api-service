package school.sptech.demo.crud.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.sptech.demo.crud.entity.Doador;
import school.sptech.demo.crud.repository.DoadorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DoadorService {
    @Autowired
    DoadorRepository repository;

    public List<Doador> getAllDoadores(){
        List<Doador> allDoadores = repository.findAll();
        return allDoadores;
    }
    public Optional<Doador> getDoadorById(Integer id){
        return repository.findById(id);
    }
    public List<Doador> getDoadorByNome(String nome){
        return repository.findByNome(nome);
    }
    public Doador postDoador(Doador doador){
        Optional<Doador> possibleDoadorWithTheSameCpf = repository.findByCpfAndIdNot(doador.getCpf(), doador.getId());
        if(possibleDoadorWithTheSameCpf.isPresent()){
            Doador doadorWithTheSameCpf = possibleDoadorWithTheSameCpf.get();
            throw new DataIntegrityViolationException("Error: There is a conflict, the CPF presented was already posted");
        }
        doador.setId(null);
        Doador doadorToBePost = repository.save(doador);
        return doadorToBePost;
    }
    public Doador putDoadorById(Integer id, Doador doador){
        if(repository.existsById(id)){
            doador.setId(id);
            Doador doadorUpdated = repository.save(doador);
            return doadorUpdated;
        }else{
            return null;
        }
    }
    public Boolean deleteDoadorById(Integer id){
       if(repository.existsById(id)){
           repository.deleteById(id);
           return true;
       }else{
           return false;
       }
    }
}
