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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import school.sptech.hub.application.service.CategoriaService;
import school.sptech.hub.domain.dto.categoria.CategoriaCreateDto;
import school.sptech.hub.domain.dto.categoria.CategoriaResponseDto;
import school.sptech.hub.infraestructure.controller.CategoriaController;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CategoriaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private CategoriaController categoriaController;

    private ObjectMapper objectMapper;
    private CategoriaResponseDto categoriaResponseDto;
    private CategoriaCreateDto categoriaCreateDto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(categoriaController).build();

        // Configurar DTO de resposta
        categoriaResponseDto = new CategoriaResponseDto();
        categoriaResponseDto.setId(1);
        categoriaResponseDto.setNome("Ficção");

        // Configurar DTO de criação
        categoriaCreateDto = new CategoriaCreateDto();
        categoriaCreateDto.setNome("Ficção");
    }

    // ===== TESTES PARA GET /categorias =====

    @Test
    @DisplayName("Deve listar todas as categorias com sucesso - GET /categorias")
    void deveListarCategoriasComSucesso() throws Exception {
        // Arrange
        CategoriaResponseDto categoria1 = new CategoriaResponseDto();
        categoria1.setId(1);
        categoria1.setNome("Ficção");

        CategoriaResponseDto categoria2 = new CategoriaResponseDto();
        categoria2.setId(2);
        categoria2.setNome("Técnico");

        List<CategoriaResponseDto> categorias = Arrays.asList(categoria1, categoria2);
        when(categoriaService.listarCategorias()).thenReturn(categorias);

        // Act & Assert
        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Ficção"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Técnico"));

        verify(categoriaService, times(1)).listarCategorias();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há categorias - GET /categorias")
    void deveRetornarListaVaziaQuandoNaoHaCategorias() throws Exception {
        // Arrange
        when(categoriaService.listarCategorias()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(categoriaService, times(1)).listarCategorias();
    }

    // ===== TESTES PARA GET /categorias/{id} =====

    @Test
    @DisplayName("Deve buscar categoria por ID com sucesso - GET /categorias/{id}")
    void deveBuscarCategoriaPorIdComSucesso() throws Exception {
        // Arrange
        when(categoriaService.buscarCategoriaPorId(1)).thenReturn(categoriaResponseDto);

        // Act & Assert
        mockMvc.perform(get("/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Ficção"));

        verify(categoriaService, times(1)).buscarCategoriaPorId(1);
    }

    @Test
    @DisplayName("Deve retornar 404 quando categoria não encontrada - GET /categorias/{id}")
    void deveRetornar404QuandoCategoriaNaoEncontrada() throws Exception {
        // Arrange
        when(categoriaService.buscarCategoriaPorId(999))
                .thenThrow(new RuntimeException("Categoria não encontrada"));

        // Act & Assert
        mockMvc.perform(get("/categorias/999"))
                .andExpect(status().isNotFound());

        verify(categoriaService, times(1)).buscarCategoriaPorId(999);
    }

    @Test
    @DisplayName("Deve validar parâmetro ID inválido - GET /categorias/{id}")
    void deveValidarParametroIdInvalido() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/categorias/abc"))
                .andExpect(status().isBadRequest());

        verify(categoriaService, never()).buscarCategoriaPorId(anyInt());
    }

    // ===== TESTES PARA POST /categorias =====

    @Test
    @DisplayName("Deve criar categoria com sucesso - POST /categorias")
    void deveCriarCategoriaComSucesso() throws Exception {
        // Arrange
        when(categoriaService.criarCategoria(any(CategoriaCreateDto.class)))
                .thenReturn(categoriaResponseDto);

        String requestBody = objectMapper.writeValueAsString(categoriaCreateDto);

        // Act & Assert
        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Ficção"));

        verify(categoriaService, times(1)).criarCategoria(any(CategoriaCreateDto.class));
    }

    @Test
    @DisplayName("Deve retornar 400 para dados inválidos - POST /categorias")
    void deveRetornar400ParaDadosInvalidos() throws Exception {
        // Arrange
        CategoriaCreateDto categoriaInvalida = new CategoriaCreateDto();
        // Deixar nome nulo para violar validação

        String requestBody = objectMapper.writeValueAsString(categoriaInvalida);

        // Act & Assert
        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());

        verify(categoriaService, never()).criarCategoria(any(CategoriaCreateDto.class));
    }

    @Test
    @DisplayName("Deve retornar 409 para conflito de integridade - POST /categorias")
    void deveRetornar409ParaConflitoDeIntegridade() throws Exception {
        // Arrange
        when(categoriaService.criarCategoria(any(CategoriaCreateDto.class)))
                .thenThrow(new DataIntegrityViolationException("Categoria já existe"));

        String requestBody = objectMapper.writeValueAsString(categoriaCreateDto);

        // Act & Assert
        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict());

        verify(categoriaService, times(1)).criarCategoria(any(CategoriaCreateDto.class));
    }

    @Test
    @DisplayName("Deve retornar 400 para body vazio - POST /categorias")
    void deveRetornar400ParaBodyVazio() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verify(categoriaService, never()).criarCategoria(any(CategoriaCreateDto.class));
    }

    @Test
    @DisplayName("Deve retornar 400 para JSON malformado - POST /categorias")
    void deveRetornar400ParaJsonMalformado() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"invalid\": \"json\"}"))
                .andExpect(status().isBadRequest());

        verify(categoriaService, never()).criarCategoria(any(CategoriaCreateDto.class));
    }

    // ===== TESTES DE CONTENT TYPE =====

    @Test
    @DisplayName("Deve retornar 415 para content type inválido - POST /categorias")
    void deveRetornar415ParaContentTypeInvalido() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("texto simples"))
                .andExpect(status().isUnsupportedMediaType());

        verify(categoriaService, never()).criarCategoria(any(CategoriaCreateDto.class));
    }

    // ===== TESTES DE INTEGRAÇÃO =====

    @Test
    @DisplayName("Deve verificar headers de resposta corretos")
    void deveVerificarHeadersDeRespostaCorretos() throws Exception {
        // Arrange
        when(categoriaService.listarCategorias()).thenReturn(List.of(categoriaResponseDto));

        // Act & Assert
        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(categoriaService, times(1)).listarCategorias();
    }

    @Test
    @DisplayName("Deve testar criação com caracteres especiais no nome")
    void deveTestarCriacaoComCaracteresEspeciais() throws Exception {
        // Arrange
        CategoriaCreateDto categoriaComAcentos = new CategoriaCreateDto();
        categoriaComAcentos.setNome("Ficção Científica & Fantasia");

        CategoriaResponseDto responseComAcentos = new CategoriaResponseDto();
        responseComAcentos.setId(1);
        responseComAcentos.setNome("Ficção Científica & Fantasia");

        when(categoriaService.criarCategoria(any(CategoriaCreateDto.class)))
                .thenReturn(responseComAcentos);

        String requestBody = objectMapper.writeValueAsString(categoriaComAcentos);

        // Act & Assert
        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Ficção Científica & Fantasia"));

        verify(categoriaService, times(1)).criarCategoria(any(CategoriaCreateDto.class));
    }
}
