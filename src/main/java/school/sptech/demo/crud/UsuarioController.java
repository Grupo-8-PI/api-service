package school.sptech.demo.crud;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> listUserById(@PathVariable Integer id){
        try{
           Optional<Usuario> usuarioAchado = repository.findById(id);
           if(usuarioAchado.isPresent()){
               Usuario usuario = usuarioAchado.get();
               return ResponseEntity.status(200).body(usuario);
           }
           return ResponseEntity.status(204).build();
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
    @PostMapping("/criar")
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuarioParaCriar){
        usuarioParaCriar.setId(null);
        try{
            repository.save(usuarioParaCriar);
            return ResponseEntity.status(200).body(usuarioParaCriar);
        }catch (ConstraintViolationException error){
            return ResponseEntity.status(500).build();
        }
    }
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Usuario> deleteUserById(@PathVariable Integer id){
        try{
            Optional<Usuario> usuarioADeletar = repository.findById(id);
                if(usuarioADeletar.isPresent()){
                    repository.deleteById(id);
                    return ResponseEntity.status(200).build();
                }else{
                    return ResponseEntity.status(204).build();
                }
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
    @PutMapping("/atualizar/${id}")
    public ResponseEntity<Usuario> updateById(@PathVariable Integer id, @RequestBody Usuario usuarioParaAtualizar){
        try {
            Optional<Usuario> usuarioAchado = repository.findById(id);

            if(usuarioAchado.isPresent()){
                usuarioParaAtualizar.setId(id);
                Usuario usuarioAtualizado = repository.save(usuarioParaAtualizar);
                return ResponseEntity.status(200).body(usuarioAtualizado);
            }
            return ResponseEntity.status(404).build();
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
}
