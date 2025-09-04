package school.sptech.hub.infraestructure.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import school.sptech.hub.domain.dto.livro.LivroComSinopseResponseDto;
import school.sptech.hub.domain.dto.livro.LivroCreateDto;
import school.sptech.hub.domain.dto.livro.LivroErroResponseSwgDto;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.application.service.LivroService;

import java.util.List;

@Tag(name = "livros", description = "Operações relacionadas aos livros disponíveis")
@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @Operation(summary = "Cadastrar um novo livro", description = "Cria um novo livro com os dados fornecidos no corpo da requisição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar o livro",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroErroResponseSwgDto.class)))
    })
    @PostMapping
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LivroResponseDto> cadastrarLivro(@Valid @RequestBody LivroCreateDto livroDto) {
        try {
            LivroResponseDto livroPostado = livroService.createNewLivro(livroDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(livroPostado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Listar todos os livros", description = "Retorna uma lista com todos os livros disponíveis no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno ao listar os livros",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroErroResponseSwgDto.class)))
    })
    @GetMapping
    public ResponseEntity<List<LivroResponseDto>> listarLivros() {
        List<LivroResponseDto> livros = livroService.listarLivros();
        return ResponseEntity.ok(livros);
    }

    @Operation(summary = "Buscar livro por ID", description = "Retorna os dados de um livro com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroErroResponseSwgDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDto> buscarLivroPorId(@PathVariable Integer id) {
        LivroResponseDto livro = livroService.buscarLivroPorId(id);
        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado");
        }
        return ResponseEntity.ok(livro);
    }

    @GetMapping("/com-sinopse/{id}")
    public ResponseEntity<LivroComSinopseResponseDto> buscarLivroPorIdComSinopse(@PathVariable Integer id) {
        LivroComSinopseResponseDto livro = livroService.buscarLivroPorIdComSinopse(id);
        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado");
        }
        return ResponseEntity.ok(livro);
    }

    @Operation(summary = "Atualizar um livro existente", description = "Atualiza os dados de um livro com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar o livro",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroErroResponseSwgDto.class)))
    })
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LivroResponseDto> atualizarLivro(@PathVariable Integer id, @Valid @RequestBody LivroCreateDto livroDto) {
        try {
            LivroResponseDto livroUpdated = livroService.atualizarLivro(id, livroDto);
            if (livroUpdated == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado");
            }
            return ResponseEntity.ok(livroUpdated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Deletar um livro", description = "Remove um livro do sistema com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroErroResponseSwgDto.class)))
    })
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarLivro(@PathVariable Integer id) {
        boolean deletado = livroService.deletarLivro(id);
        if (!deletado) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado");
        }
        return ResponseEntity.noContent().build();
    }

