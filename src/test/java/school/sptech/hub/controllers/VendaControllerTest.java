package school.sptech.hub.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import school.sptech.hub.infraestructure.controller.VendaController;
import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.application.service.VendaService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendaControllerTest {

    @Mock
    private VendaService vendaService;

    @InjectMocks
    private VendaController vendaController;

    private Venda vendaMock;
    private Venda vendaCreateMock;
    private Venda vendaUpdateMock;

    @BeforeEach
    void setUp() {
        // Arrange - Setup comum para todos os testes
        vendaMock = new Venda();
        vendaMock.setId(1);
        vendaMock.setDtReserva(LocalDateTime.now());
        vendaMock.setDtLimite(LocalDateTime.now().plusDays(7));
        vendaMock.setTotalReserva(3);
        vendaMock.setStatusReserva("CONFIRMADA");

        vendaCreateMock = new Venda();
        vendaCreateMock.setDtReserva(LocalDateTime.now());
        vendaCreateMock.setDtLimite(LocalDateTime.now().plusDays(5));
        vendaCreateMock.setTotalReserva(2);
        vendaCreateMock.setStatusReserva("PENDENTE");

        vendaUpdateMock = new Venda();
        vendaUpdateMock.setId(1);
        vendaUpdateMock.setDtReserva(LocalDateTime.now());
        vendaUpdateMock.setDtLimite(LocalDateTime.now().plusDays(10));
        vendaUpdateMock.setTotalReserva(5);
        vendaUpdateMock.setStatusReserva("ATUALIZADA");
    }

    @Test
    @DisplayName("Quando criar reserva com dados válidos deve retornar status 200 e reserva criada")
    void when_criar_reserva_with_valid_data_should_return_200_and_created_reserva() {
        // Arrange
        when(vendaService.createReserva(any(Venda.class))).thenReturn(vendaMock);

        // Act
        ResponseEntity<Venda> response = vendaController.createReserva(vendaCreateMock);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("CONFIRMADA", response.getBody().getStatusReserva());
        assertEquals(3, response.getBody().getTotalReserva());
        verify(vendaService, times(1)).createReserva(any(Venda.class));
    }

    @Test
    @DisplayName("Quando criar reserva deve verificar se objeto correto foi passado para o serviço")
    void when_criar_reserva_should_verify_correct_object_passed_to_service() {
        // Arrange
        when(vendaService.createReserva(any(Venda.class))).thenReturn(vendaMock);

        // Act
        vendaController.createReserva(vendaCreateMock);

        // Assert
        verify(vendaService, times(1)).createReserva(vendaCreateMock);
        verifyNoMoreInteractions(vendaService);
    }

    @Test
    @DisplayName("Quando atualizar reserva com dados válidos deve retornar status 200 e reserva atualizada")
    void when_atualizar_reserva_with_valid_data_should_return_200_and_updated_reserva() {
        // Arrange
        Integer idValido = 1;
        when(vendaService.updateReserva(eq(idValido), any(Venda.class))).thenReturn(vendaUpdateMock);

        // Act
        ResponseEntity<Venda> response = vendaController.updateReservaById(idValido, vendaCreateMock);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("ATUALIZADA", response.getBody().getStatusReserva());
        assertEquals(5, response.getBody().getTotalReserva());
        verify(vendaService, times(1)).updateReserva(eq(idValido), any(Venda.class));
    }

    @Test
    @DisplayName("Quando atualizar reserva com ID zero deve chamar o serviço")
    void when_atualizar_reserva_with_zero_id_should_call_service() {
        // Arrange
        Integer idZero = 0;
        when(vendaService.updateReserva(eq(idZero), any(Venda.class))).thenReturn(vendaUpdateMock);

        // Act
        ResponseEntity<Venda> response = vendaController.updateReservaById(idZero, vendaCreateMock);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(vendaService, times(1)).updateReserva(eq(idZero), any(Venda.class));
    }

    @Test
    @DisplayName("Quando atualizar reserva com ID negativo deve chamar o serviço")
    void when_atualizar_reserva_with_negative_id_should_call_service() {
        // Arrange
        Integer idNegativo = -1;
        when(vendaService.updateReserva(eq(idNegativo), any(Venda.class))).thenReturn(vendaUpdateMock);

        // Act
        ResponseEntity<Venda> response = vendaController.updateReservaById(idNegativo, vendaCreateMock);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(vendaService, times(1)).updateReserva(eq(idNegativo), any(Venda.class));
    }

    @Test
    @DisplayName("Quando buscar reserva por ID válido deve retornar status 200 e reserva encontrada")
    void when_buscar_reserva_por_id_with_valid_id_should_return_200_and_reserva() {
        // Arrange
        Integer idValido = 1;
        when(vendaService.getReservaById(idValido)).thenReturn(vendaMock);

        // Act
        ResponseEntity<Venda> response = vendaController.getReservaById(idValido);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("CONFIRMADA", response.getBody().getStatusReserva());
        assertEquals(3, response.getBody().getTotalReserva());
        verify(vendaService, times(1)).getReservaById(idValido);
    }

    @Test
    @DisplayName("Quando buscar reserva por ID zero deve chamar o serviço")
    void when_buscar_reserva_por_id_with_zero_should_call_service() {
        // Arrange
        Integer idZero = 0;
        when(vendaService.getReservaById(idZero)).thenReturn(vendaMock);

        // Act
        ResponseEntity<Venda> response = vendaController.getReservaById(idZero);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(vendaService, times(1)).getReservaById(idZero);
    }

    @Test
    @DisplayName("Quando buscar reserva por ID negativo deve chamar o serviço")
    void when_buscar_reserva_por_id_with_negative_id_should_call_service() {
        // Arrange
        Integer idNegativo = -1;
        when(vendaService.getReservaById(idNegativo)).thenReturn(vendaMock);

        // Act
        ResponseEntity<Venda> response = vendaController.getReservaById(idNegativo);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(vendaService, times(1)).getReservaById(idNegativo);
    }

    @Test
    @DisplayName("Quando buscar reserva por ID deve verificar se não há interações adicionais com o serviço")
    void when_buscar_reserva_por_id_should_verify_no_additional_service_interactions() {
        // Arrange
        Integer idValido = 1;
        when(vendaService.getReservaById(idValido)).thenReturn(vendaMock);

        // Act
        vendaController.getReservaById(idValido);

        // Assert
        verify(vendaService, times(1)).getReservaById(idValido);
        verifyNoMoreInteractions(vendaService);
    }

    @Test
    @DisplayName("Quando deletar reserva com ID válido deve retornar status 200 e reserva deletada")
    void when_deletar_reserva_with_valid_id_should_return_200_and_deleted_reserva() {
        // Arrange
        Integer idValido = 1;
        when(vendaService.deleteReservaById(idValido)).thenReturn(vendaMock);

        // Act
        ResponseEntity<Venda> response = vendaController.deleteReservaById(idValido);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("CONFIRMADA", response.getBody().getStatusReserva());
        assertEquals(3, response.getBody().getTotalReserva());
        verify(vendaService, times(1)).deleteReservaById(idValido);
    }

    @Test
    @DisplayName("Quando deletar reserva com ID zero deve chamar o serviço")
    void when_deletar_reserva_with_zero_id_should_call_service() {
        // Arrange
        Integer idZero = 0;
        when(vendaService.deleteReservaById(idZero)).thenReturn(vendaMock);

        // Act
        ResponseEntity<Venda> response = vendaController.deleteReservaById(idZero);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(vendaService, times(1)).deleteReservaById(idZero);
    }

    @Test
    @DisplayName("Quando deletar reserva com ID negativo deve chamar o serviço")
    void when_deletar_reserva_with_negative_id_should_call_service() {
        // Arrange
        Integer idNegativo = -1;
        when(vendaService.deleteReservaById(idNegativo)).thenReturn(vendaMock);

        // Act
        ResponseEntity<Venda> response = vendaController.deleteReservaById(idNegativo);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(vendaService, times(1)).deleteReservaById(idNegativo);
    }

    @Test
    @DisplayName("Quando deletar reserva deve verificar se não há interações adicionais com o serviço")
    void when_deletar_reserva_should_verify_no_additional_service_interactions() {
        // Arrange
        Integer idValido = 1;
        when(vendaService.deleteReservaById(idValido)).thenReturn(vendaMock);

        // Act
        vendaController.deleteReservaById(idValido);

        // Assert
        verify(vendaService, times(1)).deleteReservaById(idValido);
        verifyNoMoreInteractions(vendaService);
    }

    @Test
    @DisplayName("Quando atualizar reserva deve verificar se objeto correto foi passado para o serviço")
    void when_atualizar_reserva_should_verify_correct_object_passed_to_service() {
        // Arrange
        Integer idValido = 1;
        when(vendaService.updateReserva(eq(idValido), any(Venda.class))).thenReturn(vendaUpdateMock);

        // Act
        vendaController.updateReservaById(idValido, vendaCreateMock);

        // Assert
        verify(vendaService, times(1)).updateReserva(idValido, vendaCreateMock);
        verifyNoMoreInteractions(vendaService);
    }

    @Test
    @DisplayName("Quando criar reserva com objeto nulo deve chamar o serviço")
    void when_criar_reserva_with_null_object_should_call_service() {
        // Arrange
        Venda vendaNula = null;
        when(vendaService.createReserva(vendaNula)).thenReturn(vendaMock);

        // Act
        ResponseEntity<Venda> response = vendaController.createReserva(vendaNula);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(vendaService, times(1)).createReserva(vendaNula);
    }

    @Test
    @DisplayName("Quando criar reserva deve retornar exatamente o que o serviço retorna")
    void when_criar_reserva_should_return_exactly_what_service_returns() {
        // Arrange
        Venda vendaEspecifica = new Venda();
        vendaEspecifica.setId(999);
        vendaEspecifica.setStatusReserva("STATUS_ESPECÍFICO");
        vendaEspecifica.setTotalReserva(10);
        vendaEspecifica.setDtReserva(LocalDateTime.now().minusDays(1));
        vendaEspecifica.setDtLimite(LocalDateTime.now().plusDays(15));

        when(vendaService.createReserva(any(Venda.class))).thenReturn(vendaEspecifica);

        // Act
        ResponseEntity<Venda> response = vendaController.createReserva(vendaCreateMock);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(vendaEspecifica, response.getBody());
        assertEquals(999, response.getBody().getId());
        assertEquals("STATUS_ESPECÍFICO", response.getBody().getStatusReserva());
        assertEquals(10, response.getBody().getTotalReserva());
        assertNotNull(response.getBody().getDtReserva());
        assertNotNull(response.getBody().getDtLimite());
    }
}