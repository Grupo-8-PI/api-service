package school.sptech.hub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.hub.controller.dto.livro.LivroComSinopseResponseDto;
import school.sptech.hub.controller.dto.livro.LivroCreateDto;
import school.sptech.hub.controller.dto.livro.LivroErroResponseSwgDto;
import school.sptech.hub.controller.dto.livro.LivroResponseDto;
import school.sptech.hub.entity.Livro;
import school.sptech.hub.service.LivroService;

import java.util.List;

@Tag(name = "livros", description = "Operações relacionadas aos livros disponíveis")
@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @Operation(
            summary = "Cadastrar um novo livro",
            description = "Cria um novo livro com os dados fornecidos no corpo da requisição"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Livro cadastrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro ao cadastrar o livro",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroErroResponseSwgDto.class))
            )
    })
    @PostMapping
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<Livro> cadastrarLivro(@RequestBody LivroCreateDto livro) {
        Livro livroPostado = livroService.createNewLivro(livro);

            return ResponseEntity.status(200).body(livroPostado);

    }

    @Operation(
            summary = "Listar todos os livros",
            description = "Retorna uma lista com todos os livros disponíveis no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de livros retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro ao listar os livros",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroErroResponseSwgDto.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<LivroResponseDto>> listarLivros() {
        List<LivroResponseDto> livros = livroService.listarLivros();

            return ResponseEntity.status(200).body(livros);

    }

    @Operation(
            summary = "Buscar livro por ID",
            description = "Retorna os dados de um livro com base no ID fornecido"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Livro encontrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Livro não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroErroResponseSwgDto.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDto> buscarLivroPorId(@PathVariable Integer id) {
        LivroResponseDto livro = livroService.buscarLivroPorId(id);

            return ResponseEntity.status(200).body(livro);

    }

    @GetMapping("/com-sinopse/{id}")
    public ResponseEntity<LivroComSinopseResponseDto> buscarLivroPorIdComSinopse(@PathVariable Integer id) {
        LivroComSinopseResponseDto livro = livroService.buscarLivroPorIdComSinopse(id);

        return ResponseEntity.status(200).body(livro);

    }

    @Operation(
            summary = "Atualizar um livro existente",
            description = "Atualiza os dados de um livro com base no ID fornecido"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Livro atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro ao atualizar o livro",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroErroResponseSwgDto.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable Integer id, @RequestBody Livro livro) {
        Livro livroUpdated = livroService.atualizarLivro(id, livro);

            return ResponseEntity.status(200).body(livroUpdated);

    }

    @Operation(
            summary = "Deletar um livro",
            description = "Remove um livro do sistema com base no ID fornecido"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Livro deletado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Livro não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroErroResponseSwgDto.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Integer id) {
        Livro livroDeleted = livroService.deletarLivro(id);

            return ResponseEntity.status(200).build();

    }
}
