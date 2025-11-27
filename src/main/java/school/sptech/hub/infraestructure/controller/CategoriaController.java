package school.sptech.hub.infraestructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import school.sptech.hub.domain.dto.categoria.CategoriaResponseDto;
import school.sptech.hub.application.service.CategoriaService;

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
}
