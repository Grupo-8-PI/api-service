package school.sptech.hub.controller;


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
import school.sptech.hub.controller.dto.venda.VendaErroResponseSwgDto;
import school.sptech.hub.controller.dto.venda.VendaResponseDto;
import school.sptech.hub.entity.Venda;
import school.sptech.hub.service.VendaService;

@Tag(name = "reservas", description = "Operações relacionadas a venda/reserva dos livros")
@RestController
@RequestMapping("/reservas")
public class VendaController {

    @Autowired
    private VendaService service;


    @Operation(
            summary = "Criar nova reserva",
            description = "Cria uma nova reserva com os dados enviados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Reserva efetuada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou processo de reserva não finalizado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaErroResponseSwgDto.class))
            )
    })
    @PostMapping
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Venda> createReserva(@Valid @RequestBody Venda venda) {
        Venda createdVenda = service.createReserva(venda);

            return ResponseEntity.status(201).body(createdVenda);

    }

    @Operation(
            summary = "Atualizar reserva do livro",
            description = "Atualiza os dados da reserva/venda do livro com base no ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reserva atualizada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Reserva não encontrada.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaErroResponseSwgDto.class))
            )
    })
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('CLIENTE') and @vendaService.reservaPertenceAoUsuario(#id, authentication.name)")
    public ResponseEntity<Venda> updateReservaById(@PathVariable Integer id, @Valid @RequestBody Venda vendaToUpdate){
        Venda updatedVenda = service.updateReserva(id, vendaToUpdate);

        return ResponseEntity.ok(updatedVenda);
    }

    @Operation(
            summary = "Buscar reserva por ID",
            description = "Retorna os dados da reserva correspondente ao ID informado, caso exista."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reserva encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Reserva não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaErroResponseSwgDto.class))
            )
    })
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('CLIENTE') and @vendaService.reservaPertenceAoUsuario(#id, authentication.name)")
    public ResponseEntity<Venda> getReservaById(@PathVariable Integer id){
        Venda venda = service.getReservaById(id);

        return ResponseEntity.ok(venda);
    }


    @Operation(
            summary = "Deletar uma reserva por ID",
            description = "Remove a reserva correspondente ao ID informado do sistema, caso exista."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Reserva deletada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Reserva não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaErroResponseSwgDto.class))
            )
    })
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('CLIENTE') and @vendaService.reservaPertenceAoUsuario(#id, authentication.name)")
    public ResponseEntity<Venda> deleteReservaById(@PathVariable Integer id){
        Venda venda = service.deleteReservaById(id);

        return ResponseEntity.noContent().build();
    }
}