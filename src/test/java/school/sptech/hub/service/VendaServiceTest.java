package school.sptech.hub.service;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import school.sptech.hub.entity.Venda;
import school.sptech.hub.exceptions.VendaExceptions.VendaInvalidaException;
import school.sptech.hub.exceptions.VendaExceptions.VendaNaoEncontradaException;
import school.sptech.hub.repository.ReservaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VendaServiceTest {

    ReservaRepository repository = mock(ReservaRepository.class);
    VendaService vendaService = new VendaService();

    public VendaServiceTest() {
        vendaService.repository = repository;
    }

    private Venda criarVenda() {
        Venda venda = new Venda();
        venda.setId(1);
        venda.setStatusReserva("Ativa");
        // Configure outros atributos se precisar
        return venda;
    }

    @Test
    void deveCriarReservaComSucesso() {
        Venda venda = criarVenda();

        try (MockedStatic<?> mockStatic = Mockito.mockStatic(school.sptech.hub.validators.VendaValidator.class)) {
            mockStatic.when(() -> school.sptech.hub.validators.VendaValidator.isValidVenda(venda)).thenReturn(true);

            when(repository.save(venda)).thenReturn(venda);

            Venda resultado = vendaService.createReserva(venda);

            assertEquals(venda, resultado);
            verify(repository).save(venda);
        }
    }

    @Test
    void deveLancarExceptionAoCriarReservaInvalida() {
        Venda venda = criarVenda();

        try (MockedStatic<?> mockStatic = Mockito.mockStatic(school.sptech.hub.validators.VendaValidator.class)) {
            mockStatic.when(() -> school.sptech.hub.validators.VendaValidator.isValidVenda(venda)).thenReturn(false);

            assertThrows(VendaInvalidaException.class, () -> vendaService.createReserva(venda));
            verify(repository, never()).save(any());
        }
    }

    @Test
    void deveAtualizarReservaComSucesso() {
        Venda venda = criarVenda();

        try (MockedStatic<?> mockStatic = Mockito.mockStatic(school.sptech.hub.validators.VendaValidator.class)) {
            mockStatic.when(() -> school.sptech.hub.validators.VendaValidator.isValidVenda(venda)).thenReturn(true);

            when(repository.save(venda)).thenReturn(venda);

            Venda resultado = vendaService.updateReserva(venda.getId(), venda);

            assertEquals(venda, resultado);
            verify(repository).save(venda);
        }
    }

    @Test
    void deveLancarExceptionAoAtualizarReservaInvalida() {
        Venda venda = criarVenda();

        try (MockedStatic<?> mockStatic = Mockito.mockStatic(school.sptech.hub.validators.VendaValidator.class)) {
            mockStatic.when(() -> school.sptech.hub.validators.VendaValidator.isValidVenda(venda)).thenReturn(false);

            assertThrows(VendaInvalidaException.class, () -> vendaService.updateReserva(venda.getId(), venda));
            verify(repository, never()).save(any());
        }
    }

    @Test
    void deveBuscarReservaPorIdComSucesso() {
        Venda venda = criarVenda();

        when(repository.findById(venda.getId())).thenReturn(Optional.of(venda));

        Venda resultado = vendaService.getReservaById(venda.getId());

        assertEquals(venda, resultado);
        verify(repository).findById(venda.getId());
    }

    @Test
    void deveLancarExceptionAoBuscarReservaNaoExistente() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(VendaNaoEncontradaException.class, () -> vendaService.getReservaById(1));
        verify(repository).findById(1);
    }

    @Test
    void deveDeletarReservaComSucesso() {
        Venda venda = criarVenda();

        when(repository.findById(venda.getId())).thenReturn(Optional.of(venda));
        doNothing().when(repository).deleteById(venda.getId());

        Venda resultado = vendaService.deleteReservaById(venda.getId());

        assertEquals(venda, resultado);
        verify(repository).deleteById(venda.getId());
    }

    @Test
    void deveLancarExceptionAoDeletarReservaNaoExistente() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(VendaNaoEncontradaException.class, () -> vendaService.deleteReservaById(1));
        verify(repository, never()).deleteById(anyInt());
    }

}
