package school.sptech.hub.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import school.sptech.hub.application.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.application.exceptions.VendaExceptions.VendaInvalidaException;
import school.sptech.hub.application.exceptions.VendaExceptions.VendaNaoEncontradaException;
import school.sptech.hub.application.gateways.venda.VendaGateway;
import school.sptech.hub.application.gateways.usuario.UsuarioGateway;
import school.sptech.hub.application.usecases.venda.CreateVendaUseCase;
import school.sptech.hub.application.usecases.venda.GetVendaByIdUseCase;
import school.sptech.hub.application.usecases.venda.UpdateVendaReservaUseCase;
import school.sptech.hub.application.usecases.venda.DeleteVendaUseCase;
import school.sptech.hub.application.usecases.venda.ListAllVendasUseCase;
import school.sptech.hub.application.usecases.venda.ListAllVendasByClienteUseCase;
import school.sptech.hub.application.usecases.venda.CheckVendaOwnershipUseCase;
import school.sptech.hub.application.validators.VendaValidator;
import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.domain.entity.Usuario;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendaServiceTest {

    @Mock
    private VendaGateway vendaGateway;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private CreateVendaUseCase createVendaUseCase;

    @InjectMocks
    private GetVendaByIdUseCase getVendaByIdUseCase;

    @InjectMocks
    private UpdateVendaReservaUseCase updateVendaReservaUseCase;

    @InjectMocks
    private DeleteVendaUseCase deleteVendaUseCase;

    @InjectMocks
    private ListAllVendasUseCase listAllVendasUseCase;

    @InjectMocks
    private ListAllVendasByClienteUseCase listAllVendasByClienteUseCase;

    @InjectMocks
    private CheckVendaOwnershipUseCase checkVendaOwnershipUseCase;

    private Venda vendaMock;
    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        // Configurar usuário mock
        usuarioMock = new Usuario();
        usuarioMock.setId(1);
        usuarioMock.setEmail("usuario@teste.com");
        usuarioMock.setNome("Usuario Teste");

        // Configurar venda mock (entidade de domínio)
        vendaMock = new Venda();
        vendaMock.setId(1);
        vendaMock.setDtReserva(LocalDateTime.of(2023, 12, 15, 10, 30));
        vendaMock.setDtLimite(LocalDateTime.of(2023, 12, 22, 10, 30));
        vendaMock.setTotalReserva(3);
        vendaMock.setStatusReserva("ATIVA");
        vendaMock.setUsuarios(usuarioMock);
    }

    // ===== TESTES PARA CREATE VENDA USE CASE =====

    @Test
    @DisplayName("Deve criar venda com sucesso quando dados são válidos")
    void deveCriarVendaComSucesso() {
        // Arrange
        Venda novaVenda = new Venda();
        novaVenda.setTotalReserva(2);
        novaVenda.setDtLimite(LocalDateTime.of(2023, 12, 25, 10, 30));
        novaVenda.setStatusReserva("PENDENTE");

        // Configurar contexto de segurança apenas para este teste
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("usuario@teste.com");
        SecurityContextHolder.setContext(securityContext);

        try (MockedStatic<VendaValidator> mockStatic = Mockito.mockStatic(VendaValidator.class)) {
            mockStatic.when(() -> VendaValidator.isValidVenda(any(Venda.class))).thenReturn(true);
            when(usuarioGateway.findByEmail("usuario@teste.com")).thenReturn(Optional.of(usuarioMock));
            when(vendaGateway.createVenda(any(Venda.class))).thenReturn(Optional.of(vendaMock));

            // Act
            Venda resultado = createVendaUseCase.execute(novaVenda);

            // Assert
            assertNotNull(resultado);
            assertEquals(vendaMock, resultado);
            verify(vendaGateway, times(1)).createVenda(any(Venda.class));
            verify(usuarioGateway, times(1)).findByEmail("usuario@teste.com");
            mockStatic.verify(() -> VendaValidator.isValidVenda(any(Venda.class)), times(1));
        }
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar venda com dados inválidos")
    void deveLancarExcecaoAoCriarVendaComDadosInvalidos() {
        // Arrange
        Venda vendaInvalida = new Venda();
        vendaInvalida.setTotalReserva(-1);

        // Configurar contexto de segurança apenas para este teste
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("usuario@teste.com");
        SecurityContextHolder.setContext(securityContext);

        try (MockedStatic<VendaValidator> mockStatic = Mockito.mockStatic(VendaValidator.class)) {
            mockStatic.when(() -> VendaValidator.isValidVenda(any(Venda.class))).thenReturn(false);
            when(usuarioGateway.findByEmail("usuario@teste.com")).thenReturn(Optional.of(usuarioMock));

            // Act & Assert
            assertThrows(VendaInvalidaException.class, () -> createVendaUseCase.execute(vendaInvalida));
            verify(vendaGateway, never()).createVenda(any());
            mockStatic.verify(() -> VendaValidator.isValidVenda(any(Venda.class)), times(1));
        }
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não encontrado ao criar venda")
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoAoCriarVenda() {
        // Arrange
        Venda novaVenda = new Venda();

        // Configurar contexto de segurança apenas para este teste
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("usuario@teste.com");
        SecurityContextHolder.setContext(securityContext);

        when(usuarioGateway.findByEmail("usuario@teste.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsuarioNaoEncontradoException.class, () -> createVendaUseCase.execute(novaVenda));
        verify(usuarioGateway, times(1)).findByEmail("usuario@teste.com");
        verify(vendaGateway, never()).createVenda(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando falha ao criar venda no gateway")
    void deveLancarExcecaoQuandoFalhaAoCriarVendaNoGateway() {
        // Arrange
        Venda novaVenda = new Venda();
        novaVenda.setTotalReserva(2);

        // Configurar contexto de segurança apenas para este teste
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("usuario@teste.com");
        SecurityContextHolder.setContext(securityContext);

        try (MockedStatic<VendaValidator> mockStatic = Mockito.mockStatic(VendaValidator.class)) {
            mockStatic.when(() -> VendaValidator.isValidVenda(any(Venda.class))).thenReturn(true);
            when(usuarioGateway.findByEmail("usuario@teste.com")).thenReturn(Optional.of(usuarioMock));
            when(vendaGateway.createVenda(any(Venda.class))).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(VendaNaoEncontradaException.class, () -> createVendaUseCase.execute(novaVenda));
            verify(vendaGateway, times(1)).createVenda(any(Venda.class));
        }
    }

    // ===== TESTES PARA GET VENDA BY ID USE CASE =====

    @Test
    @DisplayName("Deve buscar venda por ID com sucesso")
    void deveBuscarVendaPorIdComSucesso() {
        // Arrange
        Integer idExistente = 1;
        when(vendaGateway.findById(idExistente)).thenReturn(Optional.of(vendaMock));

        // Act
        Venda resultado = getVendaByIdUseCase.execute(idExistente);

        // Assert
        assertNotNull(resultado);
        assertEquals(vendaMock, resultado);
        assertEquals(1, resultado.getId());
        assertEquals("ATIVA", resultado.getStatusReserva());
        verify(vendaGateway, times(1)).findById(idExistente);
    }

    @Test
    @DisplayName("Deve lançar exceção quando venda não encontrada por ID")
    void deveLancarExcecaoQuandoVendaNaoEncontradaPorId() {
        // Arrange
        Integer idInexistente = 999;
        when(vendaGateway.findById(idInexistente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(VendaNaoEncontradaException.class, () -> getVendaByIdUseCase.execute(idInexistente));
        verify(vendaGateway, times(1)).findById(idInexistente);
    }

    // ===== TESTES PARA UPDATE VENDA USE CASE =====

    @Test
    @DisplayName("Deve atualizar venda com sucesso quando dados são válidos")
    void deveAtualizarVendaComSucesso() {
        // Arrange
        Integer idExistente = 1;
        Venda vendaParaAtualizar = new Venda();
        vendaParaAtualizar.setTotalReserva(5);
        vendaParaAtualizar.setStatusReserva("CONFIRMADA");

        Venda vendaAtualizada = new Venda();
        vendaAtualizada.setId(1);
        vendaAtualizada.setTotalReserva(5);
        vendaAtualizada.setStatusReserva("CONFIRMADA");

        try (MockedStatic<VendaValidator> mockStatic = Mockito.mockStatic(VendaValidator.class)) {
            mockStatic.when(() -> VendaValidator.isValidVenda(any(Venda.class))).thenReturn(true);
            when(vendaGateway.updateVenda(any(Venda.class))).thenReturn(Optional.of(vendaAtualizada));

            // Act
            Venda resultado = updateVendaReservaUseCase.execute(idExistente, vendaParaAtualizar);

            // Assert
            assertNotNull(resultado);
            assertEquals(vendaAtualizada, resultado);
            verify(vendaGateway, times(1)).updateVenda(any(Venda.class));
            mockStatic.verify(() -> VendaValidator.isValidVenda(any(Venda.class)), times(1));
        }
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar venda com dados inválidos")
    void deveLancarExcecaoAoAtualizarVendaComDadosInvalidos() {
        // Arrange
        Integer idVenda = 1;
        Venda vendaParaAtualizar = new Venda();
        vendaParaAtualizar.setTotalReserva(-1); // Valor inválido

        try (MockedStatic<VendaValidator> mockStatic = Mockito.mockStatic(VendaValidator.class)) {
            mockStatic.when(() -> VendaValidator.isValidVenda(any(Venda.class))).thenReturn(false);

            // Act & Assert
            assertThrows(VendaInvalidaException.class, () -> updateVendaReservaUseCase.execute(idVenda, vendaParaAtualizar));
            verify(vendaGateway, never()).updateVenda(any());
            mockStatic.verify(() -> VendaValidator.isValidVenda(any(Venda.class)), times(1));
        }
    }

    @Test
    @DisplayName("Deve lançar exceção quando falha ao atualizar venda no gateway")
    void deveLancarExcecaoQuandoFalhaAoAtualizarVendaNoGateway() {
        // Arrange
        Integer idVenda = 1;
        Venda vendaParaAtualizar = new Venda();
        vendaParaAtualizar.setTotalReserva(5);

        try (MockedStatic<VendaValidator> mockStatic = Mockito.mockStatic(VendaValidator.class)) {
            mockStatic.when(() -> VendaValidator.isValidVenda(any(Venda.class))).thenReturn(true);
            when(vendaGateway.updateVenda(any(Venda.class))).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(VendaNaoEncontradaException.class, () -> updateVendaReservaUseCase.execute(idVenda, vendaParaAtualizar));
            verify(vendaGateway, times(1)).updateVenda(any(Venda.class));
        }
    }

    // ===== TESTES PARA DELETE VENDA USE CASE =====

    @Test
    @DisplayName("Deve deletar venda com sucesso quando existe")
    void deveDeletarVendaComSucesso() {
        // Arrange
        Integer idExistente = 1;
        when(vendaGateway.findById(idExistente)).thenReturn(Optional.of(vendaMock));
        doNothing().when(vendaGateway).deleteVenda(vendaMock);

        // Act
        Venda resultado = deleteVendaUseCase.execute(idExistente);

        // Assert
        assertNotNull(resultado);
        assertEquals(vendaMock, resultado);
        verify(vendaGateway, times(1)).findById(idExistente);
        verify(vendaGateway, times(1)).deleteVenda(vendaMock);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar venda inexistente")
    void deveLancarExcecaoAoDeletarVendaInexistente() {
        // Arrange
        Integer idInexistente = 999;
        when(vendaGateway.findById(idInexistente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(VendaNaoEncontradaException.class, () -> deleteVendaUseCase.execute(idInexistente));
        verify(vendaGateway, times(1)).findById(idInexistente);
        verify(vendaGateway, never()).deleteVenda(any());
    }

    // ===== TESTES PARA LIST ALL VENDAS USE CASE =====

    @Test
    @DisplayName("Deve listar todas as vendas com sucesso")
    void deveListarTodasAsVendasComSucesso() {
        // Arrange
        Venda venda1 = new Venda();
        venda1.setId(1);
        venda1.setStatusReserva("ATIVA");

        Venda venda2 = new Venda();
        venda2.setId(2);
        venda2.setStatusReserva("PENDENTE");

        List<Venda> vendasMock = Arrays.asList(venda1, venda2);
        when(vendaGateway.findAll()).thenReturn(vendasMock);

        // Act
        List<Venda> resultado = listAllVendasUseCase.execute();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(venda1, resultado.get(0));
        assertEquals(venda2, resultado.get(1));
        verify(vendaGateway, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há vendas")
    void deveRetornarListaVaziaQuandoNaoHaVendas() {
        // Arrange
        when(vendaGateway.findAll()).thenReturn(List.of());

        // Act
        List<Venda> resultado = listAllVendasUseCase.execute();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(vendaGateway, times(1)).findAll();
    }

    // ===== TESTES PARA LIST VENDAS BY CLIENTE USE CASE =====

    @Test
    @DisplayName("Deve listar vendas por cliente com sucesso")
    void deveListarVendasPorClienteComSucesso() {
        // Arrange
        Integer clienteId = 1;
        List<Venda> vendasMock = List.of(vendaMock);

        // Mock da verificação de existência do usuário
        when(usuarioGateway.findById(clienteId)).thenReturn(Optional.of(usuarioMock));
        when(vendaGateway.findVendasByClienteId(clienteId)).thenReturn(vendasMock);

        // Act
        List<Venda> resultado = listAllVendasByClienteUseCase.execute(clienteId);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(vendaMock, resultado.getFirst());
        verify(usuarioGateway, times(1)).findById(clienteId);
        verify(vendaGateway, times(1)).findVendasByClienteId(clienteId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente não existe")
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        // Arrange
        Integer clienteIdInexistente = 999;
        when(usuarioGateway.findById(clienteIdInexistente)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> listAllVendasByClienteUseCase.execute(clienteIdInexistente));
        assertEquals("Cliente não encontrado com ID: 999", exception.getMessage());
        verify(usuarioGateway, times(1)).findById(clienteIdInexistente);
        verify(vendaGateway, never()).findVendasByClienteId(any());
    }

    // ===== TESTES PARA CHECK VENDA OWNERSHIP USE CASE =====

    @Test
    @DisplayName("Deve verificar ownership de venda com sucesso")
    void deveVerificarOwnershipDeVendaComSucesso() {
        // Arrange
        Integer vendaId = 1;
        String userEmail = "usuario@teste.com";
        when(vendaGateway.reservaPertenceAoUsuario(vendaId, userEmail)).thenReturn(true);

        // Act
        boolean resultado = checkVendaOwnershipUseCase.execute(vendaId, userEmail);

        // Assert
        assertTrue(resultado);
        verify(vendaGateway, times(1)).reservaPertenceAoUsuario(vendaId, userEmail);
    }

    @Test
    @DisplayName("Deve retornar false quando venda não pertence ao usuário")
    void deveRetornarFalseQuandoVendaNaoPertenceAoUsuario() {
        // Arrange
        Integer vendaId = 1;
        String userEmail = "outro@teste.com";
        when(vendaGateway.reservaPertenceAoUsuario(vendaId, userEmail)).thenReturn(false);

        // Act
        boolean resultado = checkVendaOwnershipUseCase.execute(vendaId, userEmail);

        // Assert
        assertFalse(resultado);
        verify(vendaGateway, times(1)).reservaPertenceAoUsuario(vendaId, userEmail);
    }
}
