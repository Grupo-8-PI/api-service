package school.sptech.hub.infraestructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.hub.application.service.CategoriaService;
import school.sptech.hub.domain.dto.categoria.CategoriaCreateDto;
import school.sptech.hub.domain.dto.categoria.CategoriaResponseDto;

import java.util.List;

@Tag(name = "categorias", description = "Operações relacionadas às categorias dos livros")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(
            summary = "Listar todas as categorias",
            description = "Retorna uma lista com todas as categorias existentes no sistema para uso em filtros"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de categorias retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaResponseDto.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> listarCategorias() {
        List<CategoriaResponseDto> categorias = categoriaService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }

    @Operation(
            summary = "Buscar categoria por ID",
            description = "Retorna os dados de uma categoria com base no ID fornecido"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoria encontrada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria não encontrada"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> buscarCategoriaPorId(@PathVariable Integer id) {
        CategoriaResponseDto categoria = categoriaService.buscarCategoriaPorId(id);
        return ResponseEntity.ok(categoria);
    }

    @Operation(
            summary = "Criar nova categoria",
            description = "Cria uma nova categoria com os dados enviados no corpo da requisição. Os dados são validados automaticamente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Categoria criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos - erro de validação"
            )
    })
    @PostMapping
    public ResponseEntity<CategoriaResponseDto> createCategoria(@Valid @RequestBody CategoriaCreateDto categoria) {

        try {
            CategoriaResponseDto createdCategoria = categoriaService.criarCategoria(categoria);
            return ResponseEntity.status(201).body(createdCategoria);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).build();
        }
    }
}
