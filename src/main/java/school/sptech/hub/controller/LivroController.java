package school.sptech.hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.hub.entity.Livro;
import school.sptech.hub.service.LivroService;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping
    public ResponseEntity<Livro> cadastrarLivro(@RequestBody Livro livro) {
        System.out.println(livro);
        Livro livroPostado = livroService.createNewLivro(livro);
        if(livroPostado != null) {
            return ResponseEntity.status(200).body(livroPostado);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

}
