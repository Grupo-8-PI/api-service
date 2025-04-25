package school.sptech.hub.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.hub.controller.dto.VendaErroResponseDto;
import school.sptech.hub.controller.dto.VendaResponseDto;
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
                    responseCode = "200",
                    description = "Reserva efetuada co sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Processo de reserva não finalizado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaErroResponseDto.class))
            )
    })
    @PostMapping
    public ResponseEntity<Venda> createReserva(@RequestBody Venda venda) {
        Venda createdVenda = service.createReserva(venda);
        if(createdVenda != null) {
            return ResponseEntity.status(200).body(createdVenda);
        } else {
            return ResponseEntity.status(400).build();
        }
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
                    description = "Reserva com este id não encontrada.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaErroResponseDto.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Venda> updateReservaById(@PathVariable Integer id, @RequestBody Venda vendaToUpdate){
        Venda updatedVenda = service.updateReserva(id, vendaToUpdate);
        if(updatedVenda ==null){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(updatedVenda);
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
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaErroResponseDto.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Venda> getReservaById(@PathVariable Integer id){
        Venda venda = service.getReservaById(id);
        if(venda == null){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(venda);
    }


    @Operation(
            summary = "Deletar uma reserva por ID",
            description = "Remove a reserva correspondente ao ID informado do sistema, caso exista."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reserva deletada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Reserva não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaErroResponseDto.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Venda> deleteReservaById(@PathVariable Integer id){
        Venda venda = service.deleteReservaById(id);
        if(venda == null){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(venda);
    }

}
