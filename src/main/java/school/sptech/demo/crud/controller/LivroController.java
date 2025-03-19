package school.sptech.demo.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.demo.crud.entity.Doador;
import school.sptech.demo.crud.entity.Livro;
import school.sptech.demo.crud.service.LivroService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/livro")
public class LivroController {
    @Autowired
    LivroService service;

    @GetMapping
    public ResponseEntity<?> getAllLivros(){
       List<Livro> livros = service.getAllLivros();
       if(livros.isEmpty()){
           return ResponseEntity.status(204).build();
       }
       return ResponseEntity.status(200).body(livros);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getLivroById(@PathVariable Integer id){
        Livro livroFounded = service.getLivroById(id);
        if(livroFounded==null){
            return ResponseEntity.status(404).body("The book with the requested ID does not exist");
        }
        return ResponseEntity.status(200).body(livroFounded);
    }
    @GetMapping("/name_search")
    public ResponseEntity<?> getLivroByNome(@RequestParam String nome){
        List<Livro> livrosFoundWithTheSameName = service.getLivrosByNome(nome);
        if(livrosFoundWithTheSameName.size()==0){
            return ResponseEntity.status(404).build();
        }else if(livrosFoundWithTheSameName.size()>1){
            return ResponseEntity.status(409).body("Error: there were more than 1 book with the same name, it should'nt be this way, the attribute 'qtdLivros' must have been modified if more than one of a book were present");
        }else{
            return ResponseEntity.status(200).body(livrosFoundWithTheSameName.getFirst());
        }
    }
    @PostMapping
    public ResponseEntity<?> postLivro(@RequestBody Livro livro){
        Livro livroPosted = service.postLivro(livro);
        if(livroPosted==null){
            return ResponseEntity.status(409).body("There was an error, it has more than one book with the same name or ID");
        }
        return ResponseEntity.status(201).body(livroPosted);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLivroById(@PathVariable Integer id, @RequestBody Livro livro){
        Livro updatedLivro = service.putLivro(id,livro);
        if(updatedLivro==null){
            return ResponseEntity.status(404).body("The book with the requested ID does not exist");
        }
        return ResponseEntity.status(200).body(updatedLivro);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLivroById(@PathVariable Integer id){
        Livro livroDeleted = service.deleteLivroById(id);
        if(livroDeleted==null){
            return ResponseEntity.status(404).body("The book with the requested ID does not exist");
        }
        return ResponseEntity.status(200).body(livroDeleted);
    }
}
