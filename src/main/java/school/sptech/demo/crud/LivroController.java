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
    public ResponseEntity<List<Livro>> listAll(){
        List<Livro> lista = repository.findAll();
        if(lista.isEmpty()){
            return ResponseEntity.status(204).build();
        }else{
            return ResponseEntity.status(200).body(lista);
        }
    }
    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<Optional<Livro>> listById(@PathVariable Integer id){
        Optional<Livro> livroEncontrado = repository.findById(id);

        if (livroEncontrado.isPresent()){
            return ResponseEntity.status(200).body(livroEncontrado);
        }

        return ResponseEntity.status(204).build();
    }
    @PostMapping("/criar")
    public ResponseEntity<Livro> createLivro(@RequestBody Livro livroCadastro){
        livroCadastro.setId(null);
        try {
            Livro livroPostado = repository.save(livroCadastro);
            return ResponseEntity.status(201).body(livroPostado);
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Livro> updateLivro(@PathVariable Integer id, @RequestBody Livro livroParaAtualizar){

        try {
            Optional<Livro> livroAchado = repository.findById(id);

            if(livroAchado.isPresent()){
                livroParaAtualizar.setId(id);
                Livro livroAtualizado = repository.save(livroParaAtualizar);
                return ResponseEntity.status(200).body(livroAtualizado);
            }
            return ResponseEntity.status(404).build();
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }

    }
    @DeleteMapping("/remover/${id}")
    public ResponseEntity deleteLivro(@PathVariable Integer id){
        try{
            Optional<Livro> livroAchado = repository.findById(id);

            if(livroAchado.isPresent()){
                repository.deleteById(id);
                return ResponseEntity.status(200).build();
            }
            return ResponseEntity.status(404).build();

        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

}
