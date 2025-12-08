package school.sptech.hub.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioRepository;
import school.sptech.hub.infraestructure.gateways.usuario.UsuarioEntityMapper;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UsuarioControllerMeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Integer clienteUserId;
    private Integer adminUserId;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();

        Usuario cliente = new Usuario();
        cliente.setNome("Cliente Teste");
        cliente.setEmail("cliente@test.com");
        cliente.setTelefone("11999999999");
        cliente.setTipo_usuario("CLIENTE");
        cliente.setCpf("12345678900");
        cliente.setSenha("$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYIr7F5JEfG");
        cliente.setDtNascimento(LocalDate.of(1990, 1, 1));
        
        clienteUserId = usuarioRepository.save(UsuarioEntityMapper.toEntity(cliente)).getId();

        Usuario admin = new Usuario();
        admin.setNome("Admin Teste");
        admin.setEmail("admin@test.com");
        admin.setTelefone("11888888888");
        admin.setTipo_usuario("ADMIN");
        admin.setCpf("98765432100");
        admin.setSenha("$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYIr7F5JEfG");
        admin.setDtNascimento(LocalDate.of(1985, 5, 15));
        
        adminUserId = usuarioRepository.save(UsuarioEntityMapper.toEntity(admin)).getId();
    }

    @Test
    @WithMockUser(username = "cliente@test.com", roles = {"CLIENTE"})
    void getCurrentUser_WhenClienteAuthenticated_ShouldReturnOwnData() throws Exception {
        mockMvc.perform(get("/usuarios/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clienteUserId))
                .andExpect(jsonPath("$.nome").value("Cliente Teste"))
                .andExpect(jsonPath("$.email").value("cliente@test.com"))
                .andExpect(jsonPath("$.tipo_usuario").value("cliente"))
                .andExpect(jsonPath("$.cpf").value("12345678900"));
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = {"ADMIN"})
    void getCurrentUser_WhenAdminAuthenticated_ShouldReturnOwnData() throws Exception {
        mockMvc.perform(get("/usuarios/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(adminUserId))
                .andExpect(jsonPath("$.nome").value("Admin Teste"))
                .andExpect(jsonPath("$.email").value("admin@test.com"))
                .andExpect(jsonPath("$.tipo_usuario").value("admin"))
                .andExpect(jsonPath("$.cpf").value("98765432100"));
    }

    @Test
    void getCurrentUser_WhenUnauthenticated_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/usuarios/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "cliente@test.com", roles = {"CLIENTE"})
    void getCurrentUser_WhenAuthenticated_ShouldReturnLowercaseTipoUsuario() throws Exception {
        mockMvc.perform(get("/usuarios/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo_usuario").value("cliente"))
                .andExpect(jsonPath("$.tipo_usuario").isString());
    }
}

