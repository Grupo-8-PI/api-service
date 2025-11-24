package school.sptech.hub.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import school.sptech.hub.domain.dto.usuario.*;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.infraestructure.controller.UsuarioController;
import school.sptech.hub.application.usecases.usuario.*;
import school.sptech.hub.application.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerIntegrationTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

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

    private UsuarioCreateDto usuarioCreateDto;
    private UsuarioResponseDto usuarioResponseDto;
    private UsuarioLoginDto usuarioLoginDto;
    private UsuarioTokenDto usuarioTokenDto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // Para suporte a LocalDate

        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();

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

        usuarioLoginDto = new UsuarioLoginDto();
        usuarioLoginDto.setEmail("joao@email.com");
        usuarioLoginDto.setSenha("senha123");

        usuarioTokenDto = new UsuarioTokenDto();
        usuarioTokenDto.setUserId(1L);
        usuarioTokenDto.setNome("João Silva");
        usuarioTokenDto.setEmail("joao@email.com");
        usuarioTokenDto.setToken("jwt-token-example");
    }

    // ===== TESTES PARA CREATE USER =====

    @Test
    @DisplayName("POST /usuarios - Deve criar usuário com dados válidos e retornar 201")
    void deveCreateUsuarioComDadosValidosERetornar201() throws Exception {
        // Arrange
        when(createUsuarioUseCase.execute(any(UsuarioCreateDto.class))).thenReturn(usuarioResponseDto);

        // Act & Assert
        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));

        verify(createUsuarioUseCase, times(1)).execute(any(UsuarioCreateDto.class));
    }

    @Test
    @DisplayName("POST /usuarios - Deve retornar 409 quando email já existe")
    void deveRetornar409QuandoEmailJaExiste() throws Exception {
        // Arrange
        when(createUsuarioUseCase.execute(any(UsuarioCreateDto.class)))
                .thenThrow(new DataIntegrityViolationException("Email já existe"));

        // Act & Assert
        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioCreateDto)))
                .andExpect(status().isConflict());

        verify(createUsuarioUseCase, times(1)).execute(any(UsuarioCreateDto.class));
    }

    @Test
    @DisplayName("POST /usuarios - Deve retornar 400 com dados inválidos")
    void deveRetornar400ComDadosInvalidos() throws Exception {
        // Arrange
        UsuarioCreateDto usuarioInvalido = new UsuarioCreateDto();
        usuarioInvalido.setEmail(""); // Email vazio
        usuarioInvalido.setNome(""); // Nome vazio

        // Act & Assert
        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInvalido)))
                .andExpect(status().isBadRequest());

        verify(createUsuarioUseCase, never()).execute(any(UsuarioCreateDto.class));
    }

    // ===== TESTES PARA GET USER BY ID =====

    @Test
    @DisplayName("GET /usuarios/{id} - Deve buscar usuário por ID com sucesso")
    void deveBuscarUsuarioPorIdComSucesso() throws Exception {
        // Arrange
        Integer id = 1;
        when(getUserByIdUseCase.execute(id)).thenReturn(usuarioResponseDto);

        // Act & Assert
        mockMvc.perform(get("/usuarios/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));

        verify(getUserByIdUseCase, times(1)).execute(id);
    }

    @Test
    @DisplayName("GET /usuarios/{id} - Deve retornar 404 quando usuário não encontrado")
    void deveRetornar404QuandoUsuarioNaoEncontrado() throws Exception {
        // Arrange
        Integer idInexistente = 999;
        when(getUserByIdUseCase.execute(idInexistente))
                .thenThrow(new UsuarioNaoEncontradoException("Usuário não encontrado"));

        // Act & Assert
        mockMvc.perform(get("/usuarios/{id}", idInexistente))
                .andExpect(status().isNotFound());

        verify(getUserByIdUseCase, times(1)).execute(idInexistente);
    }

    // ===== TESTES PARA LOGIN =====

    @Test
    @DisplayName("POST /usuarios/login - Deve fazer login com credenciais válidas")
    void deveFazerLoginComCredenciaisValidas() throws Exception {
        // Arrange
        when(authenticateUsuarioUseCase.execute(any(Usuario.class))).thenReturn(usuarioTokenDto);

        // Act & Assert
        mockMvc.perform(post("/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioLoginDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"))
                .andExpect(jsonPath("$.token").value("jwt-token-example"));

        verify(authenticateUsuarioUseCase, times(1)).execute(any(Usuario.class));
    }

    // ===== TESTES PARA UPDATE USER =====

    @Test
    @DisplayName("PUT /usuarios/{id} - Deve atualizar usuário com sucesso")
    void deveAtualizarUsuarioComSucesso() throws Exception {
        // Arrange
        Integer id = 1;
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome("João Silva Atualizado");
        usuarioAtualizado.setEmail("joao.atualizado@email.com");
        usuarioAtualizado.setTelefone("11999999999");
        usuarioAtualizado.setTipo_usuario("cliente");
        usuarioAtualizado.setCpf("12345678900");
        usuarioAtualizado.setSenha("senha123");
        usuarioAtualizado.setDtNascimento(LocalDate.of(1990, 1, 1));

        UsuarioUpdateTokenDto responseDto = new UsuarioUpdateTokenDto();
        responseDto.setId(1);
        responseDto.setNome("João Silva Atualizado");
        responseDto.setEmail("joao.atualizado@email.com");
        responseDto.setToken("novo-token");

        when(updateUsuarioUseCase.execute(eq(id), any(Usuario.class))).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(put("/usuarios/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioAtualizado)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João Silva Atualizado"));

        verify(updateUsuarioUseCase, times(1)).execute(eq(id), any(Usuario.class));
    }

    // ===== TESTES PARA DELETE USER =====

    @Test
    @DisplayName("DELETE /usuarios/{id} - Deve deletar usuário com sucesso")
    void deveDeletarUsuarioComSucesso() throws Exception {
        // Arrange
        Integer id = 1;
        Usuario usuarioDeletado = new Usuario();
        usuarioDeletado.setId(1);
        usuarioDeletado.setNome("João Silva");
        usuarioDeletado.setEmail("joao@email.com");

        when(deleteUsuarioUseCase.execute(id)).thenReturn(usuarioDeletado);

        // Act & Assert
        mockMvc.perform(delete("/usuarios/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(deleteUsuarioUseCase, times(1)).execute(id);
    }

    // ===== TESTES DE VALIDAÇÃO =====

    @Test
    @DisplayName("Deve validar Content-Type JSON obrigatório")
    void deveValidarContentTypeJsonObrigatorio() throws Exception {
        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.TEXT_PLAIN)
                .content("dados inválidos"))
                .andExpect(status().isUnsupportedMediaType());

        verify(createUsuarioUseCase, never()).execute(any(UsuarioCreateDto.class));
    }

    // ===== TESTES ADICIONAIS DE VALIDAÇÃO =====

    @Test
    @DisplayName("Deve retornar erro de validação para dados obrigatórios ausentes")
    void deveRetornarErroValidacaoParaDadosObrigatoriosAusentes() throws Exception {
        // Arrange
        UsuarioCreateDto usuarioIncompleto = new UsuarioCreateDto();
        // Deixando campos obrigatórios vazios

        // Act & Assert
        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioIncompleto)))
                .andExpect(status().isBadRequest());

        verify(createUsuarioUseCase, never()).execute(any());
    }

    @Test
    @DisplayName("Deve retornar erro para formato de email inválido")
    void deveRetornarErroParaFormatoEmailInvalido() throws Exception {
        // Arrange
        usuarioCreateDto.setEmail("email_invalido");

        // Act & Assert
        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioCreateDto)))
                .andExpect(status().isBadRequest());

        verify(createUsuarioUseCase, never()).execute(any());
    }
}
