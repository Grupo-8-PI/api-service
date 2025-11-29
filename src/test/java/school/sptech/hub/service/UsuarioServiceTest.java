package school.sptech.hub.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import school.sptech.hub.utils.config.GerenciadorTokenJwt;
import school.sptech.hub.domain.dto.usuario.*;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.application.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.application.exceptions.UsuarioExceptions.TipoUsuarioInvalidoException;
import school.sptech.hub.application.gateways.usuario.UsuarioGateway;
import school.sptech.hub.application.usecases.usuario.CreateUsuarioUseCase;
import school.sptech.hub.application.usecases.usuario.GetUsuarioByIdUseCase;
import school.sptech.hub.application.usecases.usuario.UpdateUsuarioUseCase;
import school.sptech.hub.application.usecases.usuario.DeleteUsuarioUseCase;
import school.sptech.hub.application.usecases.usuario.AuthenticateUsuarioUseCase;
import school.sptech.hub.application.validators.UsuarioValidator;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private CreateUsuarioUseCase createUsuarioUseCase;

    @InjectMocks
    private GetUsuarioByIdUseCase getUsuarioByIdUseCase;

    @InjectMocks
    private UpdateUsuarioUseCase updateUsuarioUseCase;

    @InjectMocks
    private DeleteUsuarioUseCase deleteUsuarioUseCase;

    @InjectMocks
    private AuthenticateUsuarioUseCase authenticateUsuarioUseCase;

    private Usuario usuario;
    private UsuarioCreateDto usuarioCreateDto;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("João Silva");
        usuario.setEmail("joao@email.com");
        usuario.setTelefone("11999999999");
        usuario.setCpf("123.456.789-00");
        usuario.setTipo_usuario("cliente");
        usuario.setSenha("senha123");
        usuario.setDtNascimento(LocalDate.of(2000, 1, 1));

        usuarioCreateDto = new UsuarioCreateDto();
        usuarioCreateDto.setNome("João Silva");
        usuarioCreateDto.setEmail("joao@email.com");
        usuarioCreateDto.setTelefone("11999999999");
        usuarioCreateDto.setCpf("123.456.789-00");
        usuarioCreateDto.setTipo_usuario("cliente");
        usuarioCreateDto.setSenha("senha123");
        usuarioCreateDto.setDtNascimento(LocalDate.of(2000, 1, 1));
    }

    // ===== TESTES PARA CREATE USUARIO USE CASE =====

    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void deveCriarUsuarioComSucesso() {
        // Arrange
        try (MockedStatic<UsuarioValidator> mockStatic = Mockito.mockStatic(UsuarioValidator.class)) {
            mockStatic.when(() -> UsuarioValidator.isValidUserType("cliente")).thenReturn(true);
            when(usuarioGateway.findByEmail("joao@email.com")).thenReturn(Optional.empty());
            when(passwordEncoder.encode("senha123")).thenReturn("senhaEncriptada");
            when(usuarioGateway.createUsuario(any(Usuario.class))).thenReturn(Optional.of(usuario));

            // Act
            UsuarioResponseDto result = createUsuarioUseCase.execute(usuarioCreateDto);

            // Assert
            assertNotNull(result);
            assertEquals(usuario.getId(), result.getId());
            assertEquals(usuario.getNome(), result.getNome());
            assertEquals(usuario.getEmail(), result.getEmail());
            verify(passwordEncoder, times(1)).encode("senha123");
            verify(usuarioGateway, times(1)).createUsuario(any(Usuario.class));
            mockStatic.verify(() -> UsuarioValidator.isValidUserType("cliente"), times(1));
        }
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com tipo inválido")
    void deveLancarExcecaoAoCriarUsuarioComTipoInvalido() {
        // Arrange
        usuarioCreateDto.setTipo_usuario("tipo_invalido");

        try (MockedStatic<UsuarioValidator> mockStatic = Mockito.mockStatic(UsuarioValidator.class)) {
            mockStatic.when(() -> UsuarioValidator.isValidUserType("tipo_invalido")).thenReturn(false);

            // Act & Assert
            assertThrows(TipoUsuarioInvalidoException.class, () ->
                createUsuarioUseCase.execute(usuarioCreateDto));

            verify(usuarioGateway, never()).createUsuario(any());
            mockStatic.verify(() -> UsuarioValidator.isValidUserType("tipo_invalido"), times(1));
        }
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com email já existente")
    void deveLancarExcecaoAoCriarUsuarioComEmailJaExistente() {
        // Arrange
        try (MockedStatic<UsuarioValidator> mockStatic = Mockito.mockStatic(UsuarioValidator.class)) {
            mockStatic.when(() -> UsuarioValidator.isValidUserType("cliente")).thenReturn(true);
            when(usuarioGateway.findByEmail("joao@email.com")).thenReturn(Optional.of(usuario));

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () ->
                createUsuarioUseCase.execute(usuarioCreateDto));

            verify(usuarioGateway, never()).createUsuario(any());
            verify(passwordEncoder, never()).encode(anyString());
        }
    }

    // ===== TESTES PARA GET USUARIO BY ID USE CASE =====

    @Test
    @DisplayName("Deve buscar usuário por ID com sucesso")
    void deveBuscarUsuarioPorIdComSucesso() {
        // Arrange
        when(usuarioGateway.findById(1)).thenReturn(Optional.of(usuario));

        // Act
        UsuarioResponseDto result = getUsuarioByIdUseCase.execute(1);

        // Assert
        assertNotNull(result);
        assertEquals(usuario.getId(), result.getId());
        assertEquals(usuario.getNome(), result.getNome());
        assertEquals(usuario.getEmail(), result.getEmail());
        verify(usuarioGateway, times(1)).findById(1);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não encontrado por ID")
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoPorId() {
        // Arrange
        when(usuarioGateway.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsuarioNaoEncontradoException.class, () ->
            getUsuarioByIdUseCase.execute(999));
        verify(usuarioGateway, times(1)).findById(999);
    }

    // ===== TESTES PARA UPDATE USUARIO USE CASE =====

    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void deveAtualizarUsuarioComSucesso() {
        // Arrange
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setId(1);
        usuarioAtualizado.setNome("João Silva Atualizado");
        usuarioAtualizado.setEmail("joao.atualizado@email.com");
        usuarioAtualizado.setTelefone("11888888888");

        when(usuarioGateway.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioGateway.updateUsuario(any(Usuario.class))).thenReturn(Optional.of(usuarioAtualizado));
        when(gerenciadorTokenJwt.generateToken(any())).thenReturn("novo-token");

        // Act
        UsuarioUpdateTokenDto result = updateUsuarioUseCase.execute(1, usuarioAtualizado);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("João Silva Atualizado", result.getNome());
        assertEquals("joao.atualizado@email.com", result.getEmail());
        assertEquals("novo-token", result.getToken());
        verify(usuarioGateway, times(1)).findById(1);
        verify(usuarioGateway, times(1)).updateUsuario(any(Usuario.class));
        verify(gerenciadorTokenJwt, times(1)).generateToken(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar usuário inexistente")
    void deveLancarExcecaoAoAtualizarUsuarioInexistente() {
        // Arrange
        Usuario usuarioParaAtualizar = new Usuario();
        when(usuarioGateway.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsuarioNaoEncontradoException.class, () ->
            updateUsuarioUseCase.execute(999, usuarioParaAtualizar));
        verify(usuarioGateway, times(1)).findById(999);
        verify(usuarioGateway, never()).updateUsuario(any(Usuario.class));
    }

    // ===== TESTES PARA DELETE USUARIO USE CASE =====

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void deveDeletarUsuarioComSucesso() {
        // Arrange
        when(usuarioGateway.findById(1)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioGateway).deleteUsuario(usuario);

        // Act
        Usuario deletado = deleteUsuarioUseCase.execute(1);

        // Assert
        assertEquals(usuario, deletado);
        verify(usuarioGateway, times(1)).findById(1);
        verify(usuarioGateway, times(1)).deleteUsuario(usuario);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar usuário inexistente")
    void deveLancarExcecaoAoDeletarUsuarioInexistente() {
        // Arrange
        when(usuarioGateway.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsuarioNaoEncontradoException.class, () ->
            deleteUsuarioUseCase.execute(999));
        verify(usuarioGateway, times(1)).findById(999);
        verify(usuarioGateway, never()).deleteUsuario(any(Usuario.class));
    }

    // ===== TESTES PARA AUTHENTICATE USUARIO USE CASE =====

    @Test
    @DisplayName("Deve autenticar usuário com sucesso")
    void deveAutenticarUsuarioComSucesso() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        Usuario usuarioParaAutenticar = new Usuario();
        usuarioParaAutenticar.setEmail("joao@email.com");
        usuarioParaAutenticar.setSenha("senha123");

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(usuarioGateway.findByEmail("joao@email.com")).thenReturn(Optional.of(usuario));
        when(gerenciadorTokenJwt.generateToken(any())).thenReturn("token-fake");

        // Act
        UsuarioTokenDto tokenDto = authenticateUsuarioUseCase.execute(usuarioParaAutenticar);

        // Assert
        assertNotNull(tokenDto);
        assertEquals("token-fake", tokenDto.getToken());
        assertEquals(usuario.getId().longValue(), tokenDto.getUserId());
        assertEquals(usuario.getNome(), tokenDto.getNome());
        assertEquals(usuario.getEmail(), tokenDto.getEmail());
        verify(authenticationManager, times(1)).authenticate(any());
        verify(usuarioGateway, times(1)).findByEmail("joao@email.com");
        verify(gerenciadorTokenJwt, times(1)).generateToken(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao autenticar usuário inexistente")
    void deveLancarExcecaoAoAutenticarUsuarioInexistente() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        Usuario usuarioInexistente = new Usuario();
        usuarioInexistente.setEmail("inexistente@email.com");
        usuarioInexistente.setSenha("senha123");

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(usuarioGateway.findByEmail("inexistente@email.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsuarioNaoEncontradoException.class, () ->
            authenticateUsuarioUseCase.execute(usuarioInexistente));
        verify(authenticationManager, times(1)).authenticate(any());
        verify(usuarioGateway, times(1)).findByEmail("inexistente@email.com");
        verify(gerenciadorTokenJwt, never()).generateToken(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao autenticar com credenciais inválidas")
    void deveLancarExcecaoAoAutenticarComCredenciaisInvalidas() {
        // Arrange
        Usuario usuarioComCredenciaisInvalidas = new Usuario();
        usuarioComCredenciaisInvalidas.setEmail("joao@email.com");
        usuarioComCredenciaisInvalidas.setSenha("senha_errada");

        when(authenticationManager.authenticate(any()))
            .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () ->
            authenticateUsuarioUseCase.execute(usuarioComCredenciaisInvalidas));
        verify(authenticationManager, times(1)).authenticate(any());
        verify(usuarioGateway, never()).findByEmail(anyString());
        verify(gerenciadorTokenJwt, never()).generateToken(any());
    }

    // ===== TESTES DE VALIDAÇÃO E EDGE CASES =====

    @Test
    @DisplayName("Deve verificar se não há interações adicionais após operações bem-sucedidas")
    void deveVerificarSeNaoHaInteracoesAdicionaisAposOperacoesBemSucedidas() {
        // Arrange
        when(usuarioGateway.findById(1)).thenReturn(Optional.of(usuario));

        // Act
        getUsuarioByIdUseCase.execute(1);

        // Assert
        verify(usuarioGateway, times(1)).findById(1);
        verifyNoMoreInteractions(usuarioGateway);
    }

    @Test
    @DisplayName("Deve tratar ID muito grande adequadamente")
    void deveTratarIdMuitoGrandeAdequadamente() {
        // Arrange
        Integer idMuitoGrande = Integer.MAX_VALUE;
        when(usuarioGateway.findById(idMuitoGrande)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsuarioNaoEncontradoException.class, () ->
            getUsuarioByIdUseCase.execute(idMuitoGrande));
        verify(usuarioGateway, times(1)).findById(idMuitoGrande);
    }
}
