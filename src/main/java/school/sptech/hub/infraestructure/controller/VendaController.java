package school.sptech.hub.infraestructure.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import school.sptech.hub.application.usecases.venda.*;
import school.sptech.hub.domain.dto.venda.VendaCreateDto;
import school.sptech.hub.domain.dto.venda.VendaErroResponseSwgDto;
import school.sptech.hub.domain.dto.venda.VendaResponseDto;
import school.sptech.hub.domain.dto.venda.VendaMapper;
import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.domain.dto.venda.ReservaPaginatedResponseDto;

import java.util.List;

@Tag(name = "reservas", description = "Operações relacionadas a venda/reserva dos livros")
@RestController
@RequestMapping("/reservas")
public class VendaController {

    @Autowired
    private CreateVendaUseCase createVendaUseCase;

    @Autowired
    private UpdateVendaReservaUseCase updateVendaReservaUseCase;

    @Autowired
    private GetVendaByIdUseCase getVendaByIdUseCase;

    @Autowired
    private DeleteVendaUseCase deleteVendaUseCase;

    @Autowired
    private CheckVendaOwnershipUseCase checkVendaOwnershipUseCase;

    @Autowired
    private ListAllVendasByClienteUseCase listAllVendasByClienteUseCase;

    @Autowired
    private ListAllVendasUseCase listAllVendasUseCase;

    @Autowired
    private ListReservasPaginatedUseCase listReservasPaginatedUseCase;


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
                    description = "Processo de reserva não finalizado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaErroResponseSwgDto.class))
            )
    })
    @PostMapping
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<VendaResponseDto> createReserva(@Valid @RequestBody VendaCreateDto vendaDto) {
        Venda venda = VendaMapper.toEntity(vendaDto);
        Venda createdVenda = createVendaUseCase.execute(venda);
        VendaResponseDto response = VendaMapper.toResponseDto(createdVenda);

        return ResponseEntity.status(201).body(response);

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
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaErroResponseSwgDto.class))
            )
    })
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('CLIENTE') and @checkVendaOwnershipUseCase.execute(#id, authentication.name)")
    public ResponseEntity<VendaResponseDto> updateReservaById(@PathVariable Integer id, @RequestBody Venda vendaToUpdate){
        Venda updatedVenda = updateVendaReservaUseCase.execute(id, vendaToUpdate);
        VendaResponseDto response = VendaMapper.toResponseDto(updatedVenda);

        return ResponseEntity.status(200).body(response);
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
    @PreAuthorize("hasRole('CLIENTE') and @checkVendaOwnershipUseCase.execute(#id, authentication.name)")
    public ResponseEntity<VendaResponseDto> getReservaById(@PathVariable Integer id){
        Venda venda = getVendaByIdUseCase.execute(id);
        VendaResponseDto response = VendaMapper.toResponseDto(venda);

        return ResponseEntity.status(200).body(response);
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
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaErroResponseSwgDto.class))
            )
    })
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('CLIENTE') and @checkVendaOwnershipUseCase.execute(#id, authentication.name)")
    public ResponseEntity<VendaResponseDto> deleteReservaById(@PathVariable Integer id){
        Venda venda = deleteVendaUseCase.execute(id);
        VendaResponseDto response = VendaMapper.toResponseDto(venda);

        return ResponseEntity.status(200).body(response);
    }

    @Operation(
            summary = "Buscar reservas por ID do cliente",
            description = "Retorna todas as reservas de um cliente específico com base no ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de reservas do cliente encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado ou sem reservas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaErroResponseSwgDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - apenas clientes podem acessar suas próprias reservas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaErroResponseSwgDto.class))
            )
    })
    @GetMapping("/user/{id}")
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<VendaResponseDto>> getVendaPorIdCliente(@PathVariable Integer id){
        List<Venda> vendas = listAllVendasByClienteUseCase.execute(id);
        List<VendaResponseDto> response = vendas.stream()
                .map(VendaMapper::toResponseDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Listar todas as reservas",
            description = "Retorna todas as reservas do sistema (apenas para administradores)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de reservas encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDto.class))
            )
    })
    @GetMapping()
    @SecurityRequirement(name = "bearer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservaPaginatedResponseDto> listarReservasPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {
        ReservaPaginatedResponseDto reservas = listReservasPaginatedUseCase.execute(page, size);
        return ResponseEntity.ok(reservas);
    }
}
