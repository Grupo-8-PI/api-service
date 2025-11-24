package school.sptech.hub.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import school.sptech.hub.infraestructure.controller.VendaController;
import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.domain.dto.venda.*;
import school.sptech.hub.application.usecases.venda.*;
import school.sptech.hub.application.exceptions.VendaExceptions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendaControllerTest {

    @Mock
    private CreateVendaUseCase createVendaUseCase;

    @Mock
    private UpdateVendaReservaUseCase updateVendaReservaUseCase;

    @Mock
    private GetVendaByIdUseCase getVendaByIdUseCase;

    @Mock
    private DeleteVendaUseCase deleteVendaUseCase;

    @Mock
    private CheckVendaOwnershipUseCase checkVendaOwnershipUseCase;

    @Mock
    private ListAllVendasByClienteUseCase listAllVendasByClienteUseCase;

    @Mock
    private ListAllVendasUseCase listAllVendasUseCase;

    @InjectMocks
    private VendaController vendaController;

    private Venda vendaMock;
    private VendaCreateDto vendaCreateDto;
    private VendaResponseDto vendaResponseDto;

    @BeforeEach
    void setUp() {
        // Setup da entidade Venda
        vendaMock = new Venda();
        vendaMock.setId(1);
        vendaMock.setDtReserva(LocalDateTime.of(2023, 12, 15, 10, 30));
        vendaMock.setDtLimite(LocalDateTime.of(2023, 12, 22, 10, 30));
        vendaMock.setTotalReserva(3);
        vendaMock.setStatusReserva("CONFIRMADA");

        // Setup do DTO de criação
        vendaCreateDto = new VendaCreateDto();
        vendaCreateDto.setDtReserva(LocalDateTime.of(2023, 12, 15, 10, 30));
        vendaCreateDto.setTotalReserva(2);
        vendaCreateDto.setStatusReserva("PENDENTE");

        // Setup do DTO de resposta
        vendaResponseDto = new VendaResponseDto();
        vendaResponseDto.setId(1);
        vendaResponseDto.setDtReserva("2023-12-15T10:30:00");
        vendaResponseDto.setTotalReserva(3);
        vendaResponseDto.setStatusReserva("CONFIRMADA");
    }

    // ===== TESTES PARA CREATE RESERVA =====

    @Test
    @DisplayName("Quando criar reserva com dados válidos deve retornar status 201 e VendaResponseDto")
    void when_createReserva_withValidData_should_return201AndVendaResponseDto() {
        // Arrange
        when(createVendaUseCase.execute(any(Venda.class))).thenReturn(vendaMock);

        // Act
        ResponseEntity<VendaResponseDto> response = vendaController.createReserva(vendaCreateDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().getTotalReserva());
        assertEquals("CONFIRMADA", response.getBody().getStatusReserva());
        verify(createVendaUseCase, times(1)).execute(any(Venda.class));
    }

    @Test
    @DisplayName("Quando criar reserva com dados inválidos deve lançar exceção")
    void when_createReserva_withInvalidData_should_throwException() {
        // Arrange
        when(createVendaUseCase.execute(any(Venda.class)))
                .thenThrow(new VendaInvalidaException("Total da reserva não pode ser negativo"));

        // Act & Assert
        assertThrows(VendaInvalidaException.class, () ->
            vendaController.createReserva(vendaCreateDto)
        );
        verify(createVendaUseCase, times(1)).execute(any(Venda.class));
    }

    @Test
    @DisplayName("Quando criar reserva e ocorrer violação de integridade deve lançar exceção")
    void when_createReserva_withDataIntegrityViolation_should_throwException() {
        // Arrange
        when(createVendaUseCase.execute(any(Venda.class)))
                .thenThrow(new DataIntegrityViolationException("Violação de integridade"));

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class, () ->
            vendaController.createReserva(vendaCreateDto)
        );
        verify(createVendaUseCase, times(1)).execute(any(Venda.class));
    }

    // ===== TESTES PARA UPDATE RESERVA =====

    @Test
    @DisplayName("Quando atualizar reserva com dados válidos deve retornar status 200 e Venda")
    void when_updateReservaById_withValidData_should_return200AndVenda() {
        // Arrange
        Integer id = 1;
        Venda vendaUpdate = new Venda();
        vendaUpdate.setTotalReserva(5);
        vendaUpdate.setStatusReserva("ATUALIZADA");

        when(updateVendaReservaUseCase.execute(eq(id), any(Venda.class))).thenReturn(vendaMock);

        // Act
        ResponseEntity<Venda> response = vendaController.updateReservaById(id, vendaUpdate);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(vendaMock, response.getBody());
        verify(updateVendaReservaUseCase, times(1)).execute(eq(id), any(Venda.class));
    }

    @Test
    @DisplayName("Quando atualizar reserva inexistente deve lançar exceção")
    void when_updateReservaById_withNonExistentId_should_throwException() {
        // Arrange
        Integer idInexistente = 999;
        Venda vendaUpdate = new Venda();

        when(updateVendaReservaUseCase.execute(eq(idInexistente), any(Venda.class)))
                .thenThrow(new VendaNaoEncontradaException("Reserva não encontrada"));

        // Act & Assert
        assertThrows(VendaNaoEncontradaException.class, () ->
            vendaController.updateReservaById(idInexistente, vendaUpdate)
        );
        verify(updateVendaReservaUseCase, times(1)).execute(eq(idInexistente), any(Venda.class));
    }

    // ===== TESTES PARA GET RESERVA BY ID =====

    @Test
    @DisplayName("Quando buscar reserva por ID válido deve retornar status 200 e Venda")
    void when_getReservaById_withValidId_should_return200AndVenda() {
        // Arrange
        Integer id = 1;
        when(getVendaByIdUseCase.execute(id)).thenReturn(vendaMock);

        // Act
        ResponseEntity<Venda> response = vendaController.getReservaById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(vendaMock, response.getBody());
        verify(getVendaByIdUseCase, times(1)).execute(id);
    }

    @Test
    @DisplayName("Quando buscar reserva por ID inexistente deve lançar exceção")
    void when_getReservaById_withNonExistentId_should_throwException() {
        // Arrange
        Integer idInexistente = 999;
        when(getVendaByIdUseCase.execute(idInexistente))
                .thenThrow(new VendaNaoEncontradaException("Reserva não encontrada"));

        // Act & Assert
        assertThrows(VendaNaoEncontradaException.class, () ->
            vendaController.getReservaById(idInexistente)
        );
        verify(getVendaByIdUseCase, times(1)).execute(idInexistente);
    }

    @Test
    @DisplayName("Quando buscar reserva com ID negativo deve lançar exceção específica")
    void when_getReservaById_withNegativeId_should_throwException() {
        // Arrange
        Integer idNegativo = -1;
        when(getVendaByIdUseCase.execute(idNegativo))
                .thenThrow(new IllegalArgumentException("ID não pode ser negativo"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            vendaController.getReservaById(idNegativo)
        );
        verify(getVendaByIdUseCase, times(1)).execute(idNegativo);
    }

    // ===== TESTES PARA DELETE RESERVA =====

    @Test
    @DisplayName("Quando deletar reserva com ID válido deve retornar status 200 e Venda")
    void when_deleteReservaById_withValidId_should_return200AndVenda() {
        // Arrange
        Integer id = 1;
        when(deleteVendaUseCase.execute(id)).thenReturn(vendaMock);

        // Act
        ResponseEntity<Venda> response = vendaController.deleteReservaById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(vendaMock, response.getBody());
        verify(deleteVendaUseCase, times(1)).execute(id);
    }

    @Test
    @DisplayName("Quando deletar reserva inexistente deve lançar exceção")
    void when_deleteReservaById_withNonExistentId_should_throwException() {
        // Arrange
        Integer idInexistente = 999;
        when(deleteVendaUseCase.execute(idInexistente))
                .thenThrow(new VendaNaoEncontradaException("Reserva não encontrada"));

        // Act & Assert
        assertThrows(VendaNaoEncontradaException.class, () ->
            vendaController.deleteReservaById(idInexistente)
        );
        verify(deleteVendaUseCase, times(1)).execute(idInexistente);
    }

    // ===== TESTES PARA GET VENDAS POR ID CLIENTE =====

    @Test
    @DisplayName("Quando buscar vendas por ID cliente deve retornar status 200 e lista de VendaResponseDto")
    void when_getVendaPorIdCliente_withValidId_should_return200AndVendaResponseDtoList() {
        // Arrange
        Integer idCliente = 1;
        List<Venda> vendas = Arrays.asList(vendaMock);
        when(listAllVendasByClienteUseCase.execute(idCliente)).thenReturn(vendas);

        // Act
        ResponseEntity<List<VendaResponseDto>> response = vendaController.getVendaPorIdCliente(idCliente);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(listAllVendasByClienteUseCase, times(1)).execute(idCliente);
    }

    @Test
    @DisplayName("Quando buscar vendas por ID cliente inexistente deve retornar lista vazia")
    void when_getVendaPorIdCliente_withNonExistentId_should_returnEmptyList() {
        // Arrange
        Integer idClienteInexistente = 999;
        List<Venda> vendasVazias = Collections.emptyList();
        when(listAllVendasByClienteUseCase.execute(idClienteInexistente)).thenReturn(vendasVazias);

        // Act
        ResponseEntity<List<VendaResponseDto>> response = vendaController.getVendaPorIdCliente(idClienteInexistente);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(listAllVendasByClienteUseCase, times(1)).execute(idClienteInexistente);
    }

    // ===== TESTES PARA LIST ALL RESERVAS =====

    @Test
    @DisplayName("Quando listar todas as reservas deve retornar status 200 e lista de Venda")
    void when_listAllReservas_should_return200AndVendaList() {
        // Arrange
        List<Venda> vendas = Arrays.asList(vendaMock);
        when(listAllVendasUseCase.execute()).thenReturn(vendas);

        // Act
        ResponseEntity<List<Venda>> response = vendaController.listAllReservas();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(vendaMock, response.getBody().get(0));
        verify(listAllVendasUseCase, times(1)).execute();
    }

    @Test
    @DisplayName("Quando listar reservas e não houver nenhuma deve retornar lista vazia")
    void when_listAllReservas_withNoReservas_should_returnEmptyList() {
        // Arrange
        List<Venda> vendasVazias = Collections.emptyList();
        when(listAllVendasUseCase.execute()).thenReturn(vendasVazias);

        // Act
        ResponseEntity<List<Venda>> response = vendaController.listAllReservas();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(listAllVendasUseCase, times(1)).execute();
    }

    // ===== TESTES DE VALIDAÇÃO E VERIFICAÇÃO =====

    @Test
    @DisplayName("Deve verificar que os use cases corretos estão sendo chamados")
    void should_verifyCorrectUseCasesAreCalled() {
        // Arrange
        when(createVendaUseCase.execute(any(Venda.class))).thenReturn(vendaMock);

        // Act
        vendaController.createReserva(vendaCreateDto);

        // Assert
        verify(createVendaUseCase, times(1)).execute(any(Venda.class));
        verifyNoInteractions(updateVendaReservaUseCase, getVendaByIdUseCase, deleteVendaUseCase,
                           checkVendaOwnershipUseCase, listAllVendasByClienteUseCase, listAllVendasUseCase);
    }

    @Test
    @DisplayName("Deve verificar que update usa UpdateVendaReservaUseCase específico")
    void should_verifyUpdateUsesCorrectSpecificUseCase() {
        // Arrange
        Integer id = 1;
        Venda vendaUpdate = new Venda();
        when(updateVendaReservaUseCase.execute(id, vendaUpdate)).thenReturn(vendaMock);

        // Act
        vendaController.updateReservaById(id, vendaUpdate);

        // Assert
        verify(updateVendaReservaUseCase, times(1)).execute(id, vendaUpdate);
        verifyNoInteractions(createVendaUseCase, getVendaByIdUseCase, deleteVendaUseCase);
    }

    @Test
    @DisplayName("Quando criar reserva com total muito grande deve ser tratado")
    void when_createReserva_withVeryLargeTotal_should_beHandled() {
        // Arrange
        VendaCreateDto vendaComTotalGrande = new VendaCreateDto();
        vendaComTotalGrande.setTotalReserva(Integer.MAX_VALUE);

        when(createVendaUseCase.execute(any(Venda.class)))
                .thenReturn(vendaMock);

        // Act
        ResponseEntity<VendaResponseDto> response = vendaController.createReserva(vendaComTotalGrande);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(createVendaUseCase, times(1)).execute(any(Venda.class));
    }

    @Test
    @DisplayName("Deve verificar se todas as propriedades do response são retornadas corretamente")
    void should_verifyAllResponsePropertiesAreReturnedCorrectly() {
        // Arrange
        VendaResponseDto responseCompleto = new VendaResponseDto();
        responseCompleto.setId(1);
        responseCompleto.setTotalReserva(3);
        responseCompleto.setStatusReserva("CONFIRMADA");
        responseCompleto.setDtReserva(String.valueOf(LocalDateTime.of(2023, 12, 15, 10, 30)));

        when(getVendaByIdUseCase.execute(1)).thenReturn(vendaMock);

        // Act
        ResponseEntity<Venda> response = vendaController.getReservaById(1);

        // Assert
        assertNotNull(response.getBody());
        Venda body = response.getBody();
        assertEquals(3, body.getTotalReserva());
        assertEquals("CONFIRMADA", body.getStatusReserva());
        assertNotNull(body.getDtReserva());
        verify(getVendaByIdUseCase, times(1)).execute(1);
    }
}
