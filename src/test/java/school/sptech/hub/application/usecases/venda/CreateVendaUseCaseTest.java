package school.sptech.hub.application.usecases.venda;

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
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.application.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.application.exceptions.VendaExceptions.VendaInvalidaException;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.application.gateways.usuario.UsuarioGateway;
import school.sptech.hub.application.gateways.venda.VendaGateway;
import school.sptech.hub.application.validators.VendaValidator;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.domain.entity.Venda;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateVendaUseCaseTest {

    @Mock
    private VendaGateway vendaGateway;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private LivroGateway livroGateway;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private CreateVendaUseCase createVendaUseCase;

    private Usuario usuarioMock;
    private Livro livroMock;
    private Venda vendaMock;

    @BeforeEach
    void setUp() {
        usuarioMock = new Usuario();
        usuarioMock.setId(1);
        usuarioMock.setEmail("cliente@test.com");
        usuarioMock.setNome("Cliente Teste");

        livroMock = new Livro();
        livroMock.setId(1);
        livroMock.setTitulo("Clean Code");
        livroMock.setPreco(89.90);

        vendaMock = new Venda();
        vendaMock.setId(1);
        vendaMock.setStatusReserva("Confirmada");
        vendaMock.setLivro(livroMock);
        vendaMock.setUsuarios(usuarioMock);
    }

    @Test
    @DisplayName("Deve criar venda com dtReserva, dtLimite e totalReserva auto-setados")
    void deveCriarVendaComDadosAutoSetados() {
        LocalDateTime beforeTest = LocalDateTime.now();
        
        Venda novaVenda = new Venda();
        novaVenda.setStatusReserva("Confirmada");
        Livro livroRef = new Livro();
        livroRef.setId(1);
        novaVenda.setLivro(livroRef);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("cliente@test.com");
        SecurityContextHolder.setContext(securityContext);

        when(usuarioGateway.findByEmail("cliente@test.com")).thenReturn(Optional.of(usuarioMock));
        when(livroGateway.findById(1)).thenReturn(Optional.of(livroMock));

        Venda vendaSalva = new Venda();
        vendaSalva.setId(1);
        vendaSalva.setDtReserva(LocalDateTime.now());
        vendaSalva.setDtLimite(LocalDateTime.now().plusHours(48));
        vendaSalva.setTotalReserva(89);
        vendaSalva.setStatusReserva("Confirmada");
        vendaSalva.setLivro(livroMock);
        vendaSalva.setUsuarios(usuarioMock);

        when(vendaGateway.createVenda(any(Venda.class))).thenReturn(Optional.of(vendaSalva));

        try (MockedStatic<VendaValidator> mockStatic = Mockito.mockStatic(VendaValidator.class)) {
            mockStatic.when(() -> VendaValidator.isValidVenda(any(Venda.class))).thenReturn(true);

            Venda resultado = createVendaUseCase.execute(novaVenda);

            LocalDateTime afterTest = LocalDateTime.now();

            assertNotNull(resultado);
            assertNotNull(resultado.getDtReserva());
            assertNotNull(resultado.getDtLimite());
            assertNotNull(resultado.getTotalReserva());

            assertTrue(resultado.getDtReserva().isAfter(beforeTest.minusSeconds(1)));
            assertTrue(resultado.getDtReserva().isBefore(afterTest.plusSeconds(1)));

            assertEquals(48, java.time.Duration.between(resultado.getDtReserva(), resultado.getDtLimite()).toHours());

            assertEquals(89, resultado.getTotalReserva());

            verify(livroGateway).findById(1);
            verify(usuarioGateway).findByEmail("cliente@test.com");
            verify(vendaGateway).createVenda(any(Venda.class));
        }
    }

    @Test
    @DisplayName("Deve usar preço do livro como totalReserva")
    void deveUsarPrecoDoLivroComoTotalReserva() {
        Venda novaVenda = new Venda();
        novaVenda.setStatusReserva("Pendente");
        Livro livroRef = new Livro();
        livroRef.setId(1);
        novaVenda.setLivro(livroRef);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("cliente@test.com");
        SecurityContextHolder.setContext(securityContext);

        when(usuarioGateway.findByEmail("cliente@test.com")).thenReturn(Optional.of(usuarioMock));
        when(livroGateway.findById(1)).thenReturn(Optional.of(livroMock));

        Venda vendaSalva = new Venda();
        vendaSalva.setId(1);
        vendaSalva.setDtReserva(LocalDateTime.now());
        vendaSalva.setDtLimite(LocalDateTime.now().plusHours(48));
        vendaSalva.setTotalReserva(89);
        vendaSalva.setLivro(livroMock);
        vendaSalva.setUsuarios(usuarioMock);

        when(vendaGateway.createVenda(any(Venda.class))).thenReturn(Optional.of(vendaSalva));

        try (MockedStatic<VendaValidator> mockStatic = Mockito.mockStatic(VendaValidator.class)) {
            mockStatic.when(() -> VendaValidator.isValidVenda(any(Venda.class))).thenReturn(true);

            Venda resultado = createVendaUseCase.execute(novaVenda);

            assertEquals(89, resultado.getTotalReserva());
            verify(livroGateway).findById(1);
        }
    }

    @Test
    @DisplayName("Deve lançar exceção quando livro não existe")
    void deveLancarExcecaoQuandoLivroNaoExiste() {
        Venda novaVenda = new Venda();
        Livro livroRef = new Livro();
        livroRef.setId(999);
        novaVenda.setLivro(livroRef);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("cliente@test.com");
        SecurityContextHolder.setContext(securityContext);

        when(usuarioGateway.findByEmail("cliente@test.com")).thenReturn(Optional.of(usuarioMock));
        when(livroGateway.findById(999)).thenReturn(Optional.empty());

        assertThrows(LivroNaoEncontradoException.class, () -> createVendaUseCase.execute(novaVenda));
        verify(vendaGateway, never()).createVenda(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        Venda novaVenda = new Venda();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("naoexiste@test.com");
        SecurityContextHolder.setContext(securityContext);

        when(usuarioGateway.findByEmail("naoexiste@test.com")).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> createVendaUseCase.execute(novaVenda));
        verify(vendaGateway, never()).createVenda(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando venda inválida")
    void deveLancarExcecaoQuandoVendaInvalida() {
        Venda vendaInvalida = new Venda();
        Livro livroRef = new Livro();
        livroRef.setId(1);
        vendaInvalida.setLivro(livroRef);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("cliente@test.com");
        SecurityContextHolder.setContext(securityContext);

        when(usuarioGateway.findByEmail("cliente@test.com")).thenReturn(Optional.of(usuarioMock));
        when(livroGateway.findById(1)).thenReturn(Optional.of(livroMock));

        try (MockedStatic<VendaValidator> mockStatic = Mockito.mockStatic(VendaValidator.class)) {
            mockStatic.when(() -> VendaValidator.isValidVenda(any(Venda.class))).thenReturn(false);

            assertThrows(VendaInvalidaException.class, () -> createVendaUseCase.execute(vendaInvalida));
            verify(vendaGateway, never()).createVenda(any());
        }
    }

    @Test
    @DisplayName("Deve setar dtLimite para 48 horas após dtReserva")
    void deveSetarDtLimitePara48HorasAposDtReserva() {
        Venda novaVenda = new Venda();
        novaVenda.setStatusReserva("Confirmada");
        Livro livroRef = new Livro();
        livroRef.setId(1);
        novaVenda.setLivro(livroRef);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("cliente@test.com");
        SecurityContextHolder.setContext(securityContext);

        when(usuarioGateway.findByEmail("cliente@test.com")).thenReturn(Optional.of(usuarioMock));
        when(livroGateway.findById(1)).thenReturn(Optional.of(livroMock));

        Venda vendaSalva = new Venda();
        vendaSalva.setId(1);
        LocalDateTime dtReserva = LocalDateTime.now();
        vendaSalva.setDtReserva(dtReserva);
        vendaSalva.setDtLimite(dtReserva.plusHours(48));
        vendaSalva.setTotalReserva(89);
        vendaSalva.setLivro(livroMock);
        vendaSalva.setUsuarios(usuarioMock);

        when(vendaGateway.createVenda(any(Venda.class))).thenReturn(Optional.of(vendaSalva));

        try (MockedStatic<VendaValidator> mockStatic = Mockito.mockStatic(VendaValidator.class)) {
            mockStatic.when(() -> VendaValidator.isValidVenda(any(Venda.class))).thenReturn(true);

            Venda resultado = createVendaUseCase.execute(novaVenda);

            long horasDiferenca = java.time.Duration.between(
                resultado.getDtReserva(), 
                resultado.getDtLimite()
            ).toHours();

            assertEquals(48, horasDiferenca);
        }
    }
}

