package school.sptech.demo.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/livro")
@RestController
public class LivroController {

    @Autowired
    private LivroRepository repository;

    @GetMapping("/pesquisar")
    public ResponseEntity listAll(){
        List<Livro> lista = repository.findAll();
        if(lista.isEmpty()){
            return ResponseEntity.status(204).build();
        }else{
            return ResponseEntity.status(200).body(lista);
        }
    }
    @GetMapping("/pesquisar/{id}")
    public ResponseEntity listById(@PathVariable Integer id){
        Optional<Livro> livroEncontrado = repository.findById(id);

        if (livroEncontrado.isPresent()){
            return ResponseEntity.status(200).body(livroEncontrado);
        }

        return ResponseEntity.status(204).build();
    }
    @PostMapping("/criar")
    public ResponseEntity createLivro(@RequestBody Livro livroCadastro){
        try {
            Livro livroPostado = repository.save(livroCadastro);
            return ResponseEntity.status(201).body(livroPostado);
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error: Internal Server Error, the object could´t be created");
        }
    }
}
