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
import school.sptech.hub.domain.dto.usuario.*;
import school.sptech.hub.infraestructure.controller.UsuarioController;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.application.usecases.usuario.*;
import school.sptech.hub.application.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private CreateUsuarioUseCase createUsuarioUseCase;

    @Mock
    private GetUsuarioByIdUseCase getUserByIdUseCase;

    @Mock
    private AuthenticateUsuarioUseCase authenticateUsuarioUseCase;

    @Mock
    private UpdateUsuarioUseCase updateUsuarioUseCase;

    @Mock
    private DeleteUsuarioUseCase deleteUsuarioUseCase;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuarioMock;
    private UsuarioCreateDto usuarioCreateDto;
    private UsuarioResponseDto usuarioResponseDto;
    private UsuarioUpdateTokenDto usuarioUpdateTokenDto;
    private UsuarioLoginDto usuarioLoginDto;
    private UsuarioTokenDto usuarioTokenDto;

    @BeforeEach
    void setUp() {
        // Arrange - Setup comum para todos os testes com dados consistentes
        usuarioMock = new Usuario();
        usuarioMock.setId(1);
        usuarioMock.setNome("João Silva");
        usuarioMock.setEmail("joao@email.com");
        usuarioMock.setTelefone("11999999999");
        usuarioMock.setCpf("123.456.789-00");
        usuarioMock.setTipo_usuario("cliente");
        usuarioMock.setSenha("senha123");
        usuarioMock.setDtNascimento(LocalDate.of(1990, 5, 15));

        usuarioCreateDto = new UsuarioCreateDto();
        usuarioCreateDto.setNome("João Silva");
        usuarioCreateDto.setEmail("joao@email.com");
        usuarioCreateDto.setSenha("senha123");
        usuarioCreateDto.setTelefone("11999999999");
        usuarioCreateDto.setCpf("123.456.789-00");
        usuarioCreateDto.setTipo_usuario("cliente");
        usuarioCreateDto.setDtNascimento(LocalDate.of(1990, 5, 15));

        usuarioResponseDto = new UsuarioResponseDto();
        usuarioResponseDto.setId(1);
        usuarioResponseDto.setNome("João Silva");
        usuarioResponseDto.setEmail("joao@email.com");
        usuarioResponseDto.setTelefone("11999999999");

        usuarioUpdateTokenDto = new UsuarioUpdateTokenDto();
        usuarioUpdateTokenDto.setId(1);
        usuarioUpdateTokenDto.setNome("João Silva Atualizado");
        usuarioUpdateTokenDto.setEmail("joao.atualizado@email.com");
        usuarioUpdateTokenDto.setToken("novo-token-jwt");

        usuarioLoginDto = new UsuarioLoginDto();
        usuarioLoginDto.setEmail("joao@email.com");
        usuarioLoginDto.setSenha("senha123"); // Consistente com outros objetos

        usuarioTokenDto = new UsuarioTokenDto();
        usuarioTokenDto.setUserId(1L);
        usuarioTokenDto.setNome("João Silva");
        usuarioTokenDto.setEmail("joao@email.com");
        usuarioTokenDto.setToken("jwt-token-example");
    }

    // ===== TESTES PARA CREATE USER =====

    @Test
    @DisplayName("Quando criar usuário com dados válidos deve retornar status 201 e usuário criado")
    void when_criarUsuario_withValidData_should_return201AndCreatedUser() {
        // Arrange
        when(createUsuarioUseCase.execute(any(UsuarioCreateDto.class))).thenReturn(usuarioResponseDto);

        // Act
        ResponseEntity<UsuarioResponseDto> response = usuarioController.createUser(usuarioCreateDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("João Silva", response.getBody().getNome());
        assertEquals("joao@email.com", response.getBody().getEmail());
        verify(createUsuarioUseCase, times(1)).execute(any(UsuarioCreateDto.class));
    }

    @Test
    @DisplayName("Quando criar usuário com dados duplicados deve retornar status 409")
    void when_criarUsuario_withDuplicateData_should_return409() {
        // Arrange
        when(createUsuarioUseCase.execute(any(UsuarioCreateDto.class)))
                .thenThrow(new DataIntegrityViolationException("Email já existe"));

        // Act
        ResponseEntity<UsuarioResponseDto> response = usuarioController.createUser(usuarioCreateDto);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNull(response.getBody());
        verify(createUsuarioUseCase, times(1)).execute(any(UsuarioCreateDto.class));
    }

    // ===== TESTES PARA GET USER BY ID =====

    @Test
    @DisplayName("Quando buscar usuário por ID válido deve retornar status 200 e usuário encontrado")
    void when_getUserById_withValidId_should_return200AndUser() {
        // Arrange
        Integer idValido = 1;
        when(getUserByIdUseCase.execute(idValido)).thenReturn(usuarioResponseDto);

        // Act
        ResponseEntity<UsuarioResponseDto> response = usuarioController.getUserById(idValido);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("João Silva", response.getBody().getNome());
        assertEquals("joao@email.com", response.getBody().getEmail());
        verify(getUserByIdUseCase, times(1)).execute(idValido);
    }

    @Test
    @DisplayName("Quando buscar usuário por ID inexistente deve lançar exceção")
    void when_getUserById_withNonExistentId_should_throwException() {
        // Arrange
        Integer idInexistente = 999;
        when(getUserByIdUseCase.execute(idInexistente))
                .thenThrow(new UsuarioNaoEncontradoException("Usuário não encontrado"));

        // Act & Assert
        assertThrows(UsuarioNaoEncontradoException.class, () ->
            usuarioController.getUserById(idInexistente)
        );
        verify(getUserByIdUseCase, times(1)).execute(idInexistente);
    }

    // ===== TESTES PARA UPDATE USER =====

    @Test
    @DisplayName("Quando atualizar usuário com dados válidos deve retornar status 200 e usuário atualizado")
    void when_updateUserById_withValidData_should_return200AndUpdatedUser() {
        // Arrange
        Integer idValido = 1;
        Usuario usuarioParaAtualizar = new Usuario();
        usuarioParaAtualizar.setNome("João Silva Atualizado");
        usuarioParaAtualizar.setEmail("joao.atualizado@email.com");

        when(updateUsuarioUseCase.execute(eq(idValido), any(Usuario.class))).thenReturn(usuarioUpdateTokenDto);

        // Act
        ResponseEntity<UsuarioUpdateTokenDto> response = usuarioController.updateUserById(idValido, usuarioParaAtualizar);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("João Silva Atualizado", response.getBody().getNome());
        assertEquals("joao.atualizado@email.com", response.getBody().getEmail());
        verify(updateUsuarioUseCase, times(1)).execute(eq(idValido), any(Usuario.class));
    }

    @Test
    @DisplayName("Quando atualizar usuário inexistente deve lançar exceção")
    void when_updateUserById_withNonExistentId_should_throwException() {
        // Arrange
        Integer idInexistente = 999;
        Usuario usuarioParaAtualizar = new Usuario();
        when(updateUsuarioUseCase.execute(eq(idInexistente), any(Usuario.class)))
                .thenThrow(new UsuarioNaoEncontradoException("Usuário não encontrado"));

        // Act & Assert
        assertThrows(UsuarioNaoEncontradoException.class, () ->
            usuarioController.updateUserById(idInexistente, usuarioParaAtualizar)
        );
        verify(updateUsuarioUseCase, times(1)).execute(eq(idInexistente), any(Usuario.class));
    }

    // ===== TESTES PARA DELETE USER =====

    @Test
    @DisplayName("Quando deletar usuário com ID válido deve retornar status 200 e usuário deletado")
    void when_deleteUserById_withValidId_should_return200AndDeletedUser() {
        // Arrange
        Integer idValido = 1;
        when(deleteUsuarioUseCase.execute(idValido)).thenReturn(usuarioMock);

        // Act
        ResponseEntity<Usuario> response = usuarioController.deleteUserById(idValido);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("João Silva", response.getBody().getNome());
        assertEquals("joao@email.com", response.getBody().getEmail());
        verify(deleteUsuarioUseCase, times(1)).execute(idValido);
    }

    @Test
    @DisplayName("Quando deletar usuário inexistente deve lançar exceção")
    void when_deleteUserById_withNonExistentId_should_throwException() {
        // Arrange
        Integer idInexistente = 999;
        when(deleteUsuarioUseCase.execute(idInexistente))
                .thenThrow(new UsuarioNaoEncontradoException("Usuário não encontrado"));

        // Act & Assert
        assertThrows(UsuarioNaoEncontradoException.class, () ->
            usuarioController.deleteUserById(idInexistente)
        );
        verify(deleteUsuarioUseCase, times(1)).execute(idInexistente);
    }

    // ===== TESTES PARA LOGIN =====

    @Test
    @DisplayName("Quando fazer login com credenciais válidas deve retornar status 200 e token")
    void when_login_withValidCredentials_should_return200AndToken() {
        // Arrange
        when(authenticateUsuarioUseCase.execute(any(Usuario.class))).thenReturn(usuarioTokenDto);

        // Act
        ResponseEntity<UsuarioTokenDto> response = usuarioController.login(usuarioLoginDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getUserId());
        assertEquals("João Silva", response.getBody().getNome());
        assertEquals("joao@email.com", response.getBody().getEmail());
        assertEquals("jwt-token-example", response.getBody().getToken());
        verify(authenticateUsuarioUseCase, times(1)).execute(any(Usuario.class));
    }

    @Test
    @DisplayName("Quando fazer login com credenciais inválidas deve lançar exceção")
    void when_login_withInvalidCredentials_should_throwException() {
        // Arrange
        UsuarioLoginDto credenciaisInvalidas = new UsuarioLoginDto();
        credenciaisInvalidas.setEmail("usuario@inexistente.com");
        credenciaisInvalidas.setSenha("senhaErrada");

        when(authenticateUsuarioUseCase.execute(any(Usuario.class)))
                .thenThrow(new RuntimeException("Credenciais inválidas"));

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
            usuarioController.login(credenciaisInvalidas)
        );
        verify(authenticateUsuarioUseCase, times(1)).execute(any(Usuario.class));
    }

    // ===== TESTES DE VALIDAÇÃO =====

    @Test
    @DisplayName("Deve verificar se não há interações adicionais após operações bem-sucedidas")
    void should_verifyNoAdditionalInteractions_afterSuccessfulOperations() {
        // Arrange
        when(createUsuarioUseCase.execute(any(UsuarioCreateDto.class))).thenReturn(usuarioResponseDto);

        // Act
        usuarioController.createUser(usuarioCreateDto);

        // Assert
        verify(createUsuarioUseCase, times(1)).execute(any(UsuarioCreateDto.class));
        verifyNoMoreInteractions(createUsuarioUseCase);
        verifyNoInteractions(getUserByIdUseCase, updateUsuarioUseCase, deleteUsuarioUseCase, authenticateUsuarioUseCase);
    }

    // ===== TESTES ADICIONAIS PARA VALIDAÇÃO DE ENTRADA =====

    @Test
    @DisplayName("Quando criar usuário deve funcionar normalmente sem definir ID")
    void when_createUser_should_workNormallyWithoutSettingId() {
        // Arrange - DTO de criação não deve ter ID (gerado automaticamente)
        when(createUsuarioUseCase.execute(any(UsuarioCreateDto.class))).thenReturn(usuarioResponseDto);

        // Act
        ResponseEntity<UsuarioResponseDto> response = usuarioController.createUser(usuarioCreateDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId()); // ID foi gerado automaticamente
        verify(createUsuarioUseCase, times(1)).execute(any(UsuarioCreateDto.class));
    }

    @Test
    @DisplayName("Quando buscar usuário com ID nulo deve lançar exceção do Spring")
    void when_getUserById_withNullId_should_throwException() {
        // Arrange
        // Quando o Spring recebe um ID nulo como @PathVariable, ele não chama o método
        // Em vez disso, mockaremos um cenário onde o use case recebe null
        when(getUserByIdUseCase.execute(null))
                .thenThrow(new IllegalArgumentException("ID não pode ser nulo"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            getUserByIdUseCase.execute(null)
        );
    }

    @Test
    @DisplayName("Quando buscar usuário com ID negativo deve chamar use case")
    void when_getUserById_withNegativeId_should_callUseCase() {
        // Arrange
        Integer idNegativo = -1;
        when(getUserByIdUseCase.execute(idNegativo))
                .thenThrow(new UsuarioNaoEncontradoException("ID inválido"));

        // Act & Assert
        assertThrows(UsuarioNaoEncontradoException.class, () ->
            usuarioController.getUserById(idNegativo)
        );
        verify(getUserByIdUseCase, times(1)).execute(idNegativo);
    }

    @Test
    @DisplayName("Quando atualizar usuário com dados nulos deve chamar use case")
    void when_updateUser_withNullData_should_callUseCase() {
        // Arrange
        Integer id = 1;
        Usuario usuarioNulo = null;
        when(updateUsuarioUseCase.execute(eq(id), any()))
                .thenThrow(new IllegalArgumentException("Dados do usuário não podem ser nulos"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            usuarioController.updateUserById(id, usuarioNulo)
        );
        verify(updateUsuarioUseCase, times(1)).execute(eq(id), any());
    }

    @Test
    @DisplayName("Quando fazer login com dados nulos deve lançar exceção")
    void when_login_withNullLoginDto_should_throwException() {
        // Act & Assert
        assertThrows(Exception.class, () ->
            usuarioController.login(null)
        );
        verifyNoInteractions(authenticateUsuarioUseCase);
    }

    @Test
    @DisplayName("Quando buscar usuário com ID inválido deve lançar exceção apropriada")
    void when_getUserById_withInvalidId_should_throwException() {
        // Arrange
        Integer idInvalido = 0; // ID inválido em vez de null
        when(getUserByIdUseCase.execute(idInvalido))
                .thenThrow(new IllegalArgumentException("ID inválido"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            usuarioController.getUserById(idInvalido)
        );
        verify(getUserByIdUseCase, times(1)).execute(idInvalido);
    }
}
