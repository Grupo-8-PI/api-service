package school.sptech.hub.infraestructure.controller;

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
import school.sptech.hub.domain.dto.livro.LivroComSinopseResponseDto;
import school.sptech.hub.domain.dto.livro.LivroCreateDto;
import school.sptech.hub.domain.dto.livro.LivroErroResponseSwgDto;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.dto.livro.LivroUpdateDto;
import school.sptech.hub.application.service.LivroService;

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
                    responseCode = "201",
                    description = "Livro cadastrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroErroResponseSwgDto.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Livro já existe (ISBN duplicado)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroErroResponseSwgDto.class))
            )
    })
    @PostMapping
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LivroResponseDto> cadastrarLivro(@Valid @RequestBody LivroCreateDto livro) {
        LivroResponseDto livroPostado = livroService.createNewLivro(livro);
        return ResponseEntity.status(201).body(livroPostado);
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
            )
    })
    @GetMapping
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<List<LivroResponseDto>> listarLivros() {
        List<LivroResponseDto> livros = livroService.listarLivros();
        return ResponseEntity.ok(livros);
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<LivroResponseDto> buscarLivroPorId(@PathVariable Integer id) {
        LivroResponseDto livro = livroService.buscarLivroPorId(id);
        return ResponseEntity.ok(livro);
    }

    @Operation(
            summary = "Buscar livro por ID com sinopse",
            description = "Retorna os dados de um livro com sinopse gerada por IA"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Livro encontrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroComSinopseResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Livro não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroErroResponseSwgDto.class))
            )
    })
    @GetMapping("/com-sinopse/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<LivroComSinopseResponseDto> buscarLivroPorIdComSinopse(@PathVariable Integer id) {
        LivroComSinopseResponseDto livro = livroService.buscarLivroPorIdComSinopse(id);
        return ResponseEntity.ok(livro);
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
                    description = "Dados inválidos fornecidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroErroResponseSwgDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Livro não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroErroResponseSwgDto.class))
            )
    })
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LivroResponseDto> atualizarLivro(@PathVariable Integer id, @Valid @RequestBody LivroUpdateDto livroUpdateDto) {
        LivroResponseDto livroUpdated = livroService.atualizarLivro(id, livroUpdateDto);
        return ResponseEntity.ok(livroUpdated);
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
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LivroResponseDto> deletarLivro(@PathVariable Integer id) {
        LivroResponseDto livroDeleted = livroService.deletarLivro(id);
        return ResponseEntity.ok(livroDeleted);
    }
}
