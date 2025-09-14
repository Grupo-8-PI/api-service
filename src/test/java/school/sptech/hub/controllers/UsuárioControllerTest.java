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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

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
        // Arrange - Setup comum para todos os testes
        usuarioMock = new Usuario();
        usuarioMock.setId(1);
        usuarioMock.setNome("João Silva");
        usuarioMock.setEmail("joao@email.com");

        usuarioCreateDto = new UsuarioCreateDto();
        usuarioCreateDto.setNome("João Silva");
        usuarioCreateDto.setEmail("joao@email.com");
        usuarioCreateDto.setSenha("123456");

        usuarioResponseDto = new UsuarioResponseDto();
        usuarioResponseDto.setId(1);
        usuarioResponseDto.setNome("João Silva");
        usuarioResponseDto.setEmail("joao@email.com");

        usuarioUpdateTokenDto = new UsuarioUpdateTokenDto();
        usuarioUpdateTokenDto.setId(1);
        usuarioUpdateTokenDto.setNome("João Silva Atualizado");
        usuarioUpdateTokenDto.setEmail("joao.atualizado@email.com");

        usuarioLoginDto = new UsuarioLoginDto();
        usuarioLoginDto.setEmail("joao@email.com");
        usuarioLoginDto.setSenha("123456");

        usuarioTokenDto = new UsuarioTokenDto();
        usuarioTokenDto.setUserId(1L);
        usuarioTokenDto.setNome("João Silva");
        usuarioTokenDto.setEmail("joao@email.com");
        usuarioTokenDto.setToken("jwt-token-example");
    }

    @Test
    @DisplayName("Quando criar usuário com dados válidos deve retornar status 201 e usuário criado")
    void when_criar_usuario_with_valid_data_should_return_201_and_created_user() {
        // Arrange
        when(usuarioService.createUser(any(UsuarioCreateDto.class))).thenReturn(usuarioResponseDto);

        // Act
        ResponseEntity<UsuarioResponseDto> response = usuarioController.createUser(usuarioCreateDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("João Silva", response.getBody().getNome());
        assertEquals("joao@email.com", response.getBody().getEmail());
        verify(usuarioService, times(1)).createUser(any(UsuarioCreateDto.class));
    }

    @Test
    @DisplayName("Quando criar usuário com dados duplicados deve retornar status 409")
    void when_criar_usuario_with_duplicate_data_should_return_409() {
        // Arrange
        when(usuarioService.createUser(any(UsuarioCreateDto.class)))
                .thenThrow(new DataIntegrityViolationException("Email já existe"));

        // Act
        ResponseEntity<UsuarioResponseDto> response = usuarioController.createUser(usuarioCreateDto);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNull(response.getBody());
        verify(usuarioService, times(1)).createUser(any(UsuarioCreateDto.class));
    }

    @Test
    @DisplayName("Quando buscar usuário por ID válido deve retornar status 200 e usuário encontrado")
    void when_buscar_usuario_por_id_with_valid_id_should_return_200_and_user() {
        // Arrange
        Integer idValido = 1;
        when(usuarioService.getUserById(idValido)).thenReturn(usuarioResponseDto);

        // Act
        ResponseEntity<UsuarioResponseDto> response = usuarioController.getUserById(idValido);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("João Silva", response.getBody().getNome());
        assertEquals("joao@email.com", response.getBody().getEmail());
        verify(usuarioService, times(1)).getUserById(idValido);
    }

    @Test
    @DisplayName("Quando buscar usuário por ID zero deve chamar o serviço")
    void when_buscar_usuario_por_id_with_zero_should_call_service() {
        // Arrange
        Integer idZero = 0;
        when(usuarioService.getUserById(idZero)).thenReturn(usuarioResponseDto);

        // Act
        ResponseEntity<UsuarioResponseDto> response = usuarioController.getUserById(idZero);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(usuarioService, times(1)).getUserById(idZero);
    }

    @Test
    @DisplayName("Quando buscar usuário por ID negativo deve chamar o serviço")
    void when_buscar_usuario_por_id_with_negative_id_should_call_service() {
        // Arrange
        Integer idNegativo = -1;
        when(usuarioService.getUserById(idNegativo)).thenReturn(usuarioResponseDto);

        // Act
        ResponseEntity<UsuarioResponseDto> response = usuarioController.getUserById(idNegativo);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(usuarioService, times(1)).getUserById(idNegativo);
    }

    @Test
    @DisplayName("Quando atualizar usuário com dados válidos deve retornar status 200 e usuário atualizado")
    void when_atualizar_usuario_with_valid_data_should_return_200_and_updated_user() {
        // Arrange
        Integer idValido = 1;
        Usuario usuarioParaAtualizar = new Usuario();
        usuarioParaAtualizar.setNome("João Silva Atualizado");
        usuarioParaAtualizar.setEmail("joao.atualizado@email.com");

        when(usuarioService.updateUserById(eq(idValido), any(Usuario.class))).thenReturn(usuarioUpdateTokenDto);

        // Act
        ResponseEntity<UsuarioUpdateTokenDto> response = usuarioController.updateUserById(idValido, usuarioParaAtualizar);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("João Silva Atualizado", response.getBody().getNome());
        assertEquals("joao.atualizado@email.com", response.getBody().getEmail());
        verify(usuarioService, times(1)).updateUserById(eq(idValido), any(Usuario.class));
    }

    @Test
    @DisplayName("Quando atualizar usuário com ID zero deve chamar o serviço")
    void when_atualizar_usuario_with_zero_id_should_call_service() {
        // Arrange
        Integer idZero = 0;
        Usuario usuarioParaAtualizar = new Usuario();
        when(usuarioService.updateUserById(eq(idZero), any(Usuario.class))).thenReturn(usuarioUpdateTokenDto);

        // Act
        ResponseEntity<UsuarioUpdateTokenDto> response = usuarioController.updateUserById(idZero, usuarioParaAtualizar);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(usuarioService, times(1)).updateUserById(eq(idZero), any(Usuario.class));
    }

    @Test
    @DisplayName("Quando deletar usuário com ID válido deve retornar status 200 e usuário deletado")
    void when_deletar_usuario_with_valid_id_should_return_200_and_deleted_user() {
        // Arrange
        Integer idValido = 1;
        when(usuarioService.deleteUserById(idValido)).thenReturn(usuarioMock);

        // Act
        ResponseEntity<Usuario> response = usuarioController.deleteUserById(idValido);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("João Silva", response.getBody().getNome());
        assertEquals("joao@email.com", response.getBody().getEmail());
        verify(usuarioService, times(1)).deleteUserById(idValido);
    }

    @Test
    @DisplayName("Quando deletar usuário com ID zero deve chamar o serviço")
    void when_deletar_usuario_with_zero_id_should_call_service() {
        // Arrange
        Integer idZero = 0;
        when(usuarioService.deleteUserById(idZero)).thenReturn(usuarioMock);

        // Act
        ResponseEntity<Usuario> response = usuarioController.deleteUserById(idZero);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(usuarioService, times(1)).deleteUserById(idZero);
    }

    @Test
    @DisplayName("Quando fazer login com credenciais válidas deve retornar status 200 e token")
    void when_login_with_valid_credentials_should_return_200_and_token() {
        // Arrange
        when(usuarioService.autenticar(any(Usuario.class))).thenReturn(usuarioTokenDto);

        // Act
        ResponseEntity<UsuarioTokenDto> response = usuarioController.login(usuarioLoginDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getUserId());
        assertEquals("João Silva", response.getBody().getNome());
        assertEquals("joao@email.com", response.getBody().getEmail());
        assertEquals("jwt-token-example", response.getBody().getToken());
        verify(usuarioService, times(1)).autenticar(any(Usuario.class));
    }

    @Test
    @DisplayName("Quando criar usuário deve verificar se objeto correto foi passado para o serviço")
    void when_criar_usuario_should_verify_correct_object_passed_to_service() {
        // Arrange
        when(usuarioService.createUser(any(UsuarioCreateDto.class))).thenReturn(usuarioResponseDto);

        // Act
        usuarioController.createUser(usuarioCreateDto);

        // Assert
        verify(usuarioService, times(1)).createUser(usuarioCreateDto);
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    @DisplayName("Quando buscar usuário por ID deve verificar se não há interações adicionais com o serviço")
    void when_buscar_usuario_por_id_should_verify_no_additional_service_interactions() {
        // Arrange
        Integer idValido = 1;
        when(usuarioService.getUserById(idValido)).thenReturn(usuarioResponseDto);

        // Act
        usuarioController.getUserById(idValido);

        // Assert
        verify(usuarioService, times(1)).getUserById(idValido);
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    @DisplayName("Quando fazer login deve verificar se não há interações adicionais com o serviço")
    void when_login_should_verify_no_additional_service_interactions() {
        // Arrange
        when(usuarioService.autenticar(any(Usuario.class))).thenReturn(usuarioTokenDto);

        // Act
        usuarioController.login(usuarioLoginDto);

        // Assert
        verify(usuarioService, times(1)).autenticar(any(Usuario.class));
        verifyNoMoreInteractions(usuarioService);
    }
}