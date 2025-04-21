package school.sptech.hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;
import school.sptech.hub.controller.dto.LivroCreateDto;
import school.sptech.hub.controller.dto.LivroResponseDto;
import school.sptech.hub.entity.Livro;
import school.sptech.hub.service.LivroService;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping
    public ResponseEntity<Livro> cadastrarLivro(@RequestBody LivroCreateDto livro) {
        System.out.println(livro);
        Livro livroPostado = livroService.createNewLivro(livro);
        if(livroPostado != null) {
            return ResponseEntity.status(200).body(livroPostado);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDto>> listarLivros() {
        List<LivroResponseDto> livros = livroService.listarLivros();
        if(livros != null) {
            return ResponseEntity.status(200).body(livros);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDto> buscarLivroPorId(@PathVariable Integer id) {
        LivroResponseDto livro = livroService.buscarLivroPorId(id);
        if(livro != null) {
            return ResponseEntity.status(200).body(livro);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable Integer id, @RequestBody Livro livro) {
        Livro livroUpdated = livroService.atualizarLivro(id, livro);
        if(livroUpdated != null) {
            return ResponseEntity.status(200).body(livroUpdated);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Integer id) {
        Livro livroDeleted = livroService.deletarLivro(id);
        if(livroDeleted == null){
            return ResponseEntity.status(404).build();
        } else {
            return ResponseEntity.status(200).build();
        }
    }
}
