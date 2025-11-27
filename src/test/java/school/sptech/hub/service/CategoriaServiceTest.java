package school.sptech.hub.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.hub.application.service.CategoriaService;
import school.sptech.hub.application.usecases.categoria.CreateCategoriaUseCase;
import school.sptech.hub.application.usecases.categoria.FindCategoriaByIdUseCase;
import school.sptech.hub.application.usecases.categoria.ListAllCategoriasUseCase;
import school.sptech.hub.domain.dto.categoria.CategoriaCreateDto;
import school.sptech.hub.domain.dto.categoria.CategoriaResponseDto;
import school.sptech.hub.domain.entity.Categoria;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private FindCategoriaByIdUseCase findCategoriaByIdUseCase;

    @Mock
    private ListAllCategoriasUseCase listAllCategoriasUseCase;

    @Mock
    private CreateCategoriaUseCase createCategoriaUseCase;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoriaMock;
    private CategoriaCreateDto categoriaCreateDto;
    private CategoriaResponseDto categoriaResponseDto;

    @BeforeEach
    void setUp() {
        // Configurar categoria mock (entidade de domínio)
        categoriaMock = new Categoria();
        categoriaMock.setId(1);
        categoriaMock.setNome("Ficção");

        // Configurar DTO de criação
        categoriaCreateDto = new CategoriaCreateDto();
        categoriaCreateDto.setNome("Ficção");

        // Configurar DTO de resposta
        categoriaResponseDto = new CategoriaResponseDto();
        categoriaResponseDto.setId(1);
        categoriaResponseDto.setNome("Ficção");
    }

    // ===== TESTES PARA LISTAR CATEGORIAS =====

    @Test
    @DisplayName("Deve listar todas as categorias com sucesso")
    void deveListarCategoriasComSucesso() {
        // Arrange
        CategoriaResponseDto categoria1 = new CategoriaResponseDto();
        categoria1.setId(1);
        categoria1.setNome("Ficção");

        CategoriaResponseDto categoria2 = new CategoriaResponseDto();
        categoria2.setId(2);
        categoria2.setNome("Técnico");

        List<CategoriaResponseDto> categoriasEsperadas = Arrays.asList(categoria1, categoria2);
        when(listAllCategoriasUseCase.execute()).thenReturn(categoriasEsperadas);

        // Act
        List<CategoriaResponseDto> resultado = categoriaService.listarCategorias();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(categoriasEsperadas, resultado);
        verify(listAllCategoriasUseCase, times(1)).execute();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há categorias")
    void deveRetornarListaVaziaQuandoNaoHaCategorias() {
        // Arrange
        when(listAllCategoriasUseCase.execute()).thenReturn(List.of());

        // Act
        List<CategoriaResponseDto> resultado = categoriaService.listarCategorias();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(listAllCategoriasUseCase, times(1)).execute();
    }

    // ===== TESTES PARA BUSCAR CATEGORIA POR ID =====

    @Test
    @DisplayName("Deve buscar categoria por ID com sucesso")
    void deveBuscarCategoriaPorIdComSucesso() {
        // Arrange
        Integer idExistente = 1;
        when(findCategoriaByIdUseCase.execute(idExistente)).thenReturn(categoriaResponseDto);

        // Act
        CategoriaResponseDto resultado = categoriaService.buscarCategoriaPorId(idExistente);

        // Assert
        assertNotNull(resultado);
        assertEquals(categoriaResponseDto.getId(), resultado.getId());
        assertEquals(categoriaResponseDto.getNome(), resultado.getNome());
        verify(findCategoriaByIdUseCase, times(1)).execute(idExistente);
    }

    @Test
    @DisplayName("Deve propagar exceção quando categoria não encontrada")
    void devePropaginExcecaoQuandoCategoriaNaoEncontrada() {
        // Arrange
        Integer idInexistente = 999;
        RuntimeException exception = new RuntimeException("Categoria não encontrada");
        when(findCategoriaByIdUseCase.execute(idInexistente)).thenThrow(exception);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class,
            () -> categoriaService.buscarCategoriaPorId(idInexistente));

        assertEquals("Categoria não encontrada", thrown.getMessage());
        verify(findCategoriaByIdUseCase, times(1)).execute(idInexistente);
    }

    // ===== TESTES PARA CRIAR CATEGORIA =====

    @Test
    @DisplayName("Deve criar categoria com sucesso")
    void deveCriarCategoriaComSucesso() {
        // Arrange
        when(createCategoriaUseCase.execute(any(Categoria.class))).thenReturn(categoriaMock);

        // Act
        CategoriaResponseDto resultado = categoriaService.criarCategoria(categoriaCreateDto);

        // Assert
        assertNotNull(resultado);
        assertEquals(categoriaMock.getId(), resultado.getId());
        assertEquals(categoriaMock.getNome(), resultado.getNome());
        verify(createCategoriaUseCase, times(1)).execute(any(Categoria.class));
    }

    @Test
    @DisplayName("Deve propagar exceção quando falha ao criar categoria")
    void devePropaginExcecaoQuandoFalhaAoCriarCategoria() {
        // Arrange
        RuntimeException exception = new RuntimeException("Erro ao criar categoria");
        when(createCategoriaUseCase.execute(any(Categoria.class))).thenThrow(exception);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class,
            () -> categoriaService.criarCategoria(categoriaCreateDto));

        assertEquals("Erro ao criar categoria", thrown.getMessage());
        verify(createCategoriaUseCase, times(1)).execute(any(Categoria.class));
    }

    @Test
    @DisplayName("Deve tratar categoria com dados nulos")
    void deveTratarCategoriaComDadosNulos() {
        // Arrange
        CategoriaCreateDto dtoComDadosNulos = new CategoriaCreateDto();
        // Deixar campos nulos intencionalmente

        when(createCategoriaUseCase.execute(any(Categoria.class))).thenReturn(categoriaMock);

        // Act
        CategoriaResponseDto resultado = categoriaService.criarCategoria(dtoComDadosNulos);

        // Assert
        assertNotNull(resultado);
        verify(createCategoriaUseCase, times(1)).execute(any(Categoria.class));
    }

    // ===== TESTES DE INTEGRAÇÃO DOS MÉTODOS =====

    @Test
    @DisplayName("Deve verificar se todos os use cases são chamados corretamente")
    void deveVerificarChamadasDosUseCases() {
        // Arrange
        when(listAllCategoriasUseCase.execute()).thenReturn(List.of(categoriaResponseDto));
        when(findCategoriaByIdUseCase.execute(anyInt())).thenReturn(categoriaResponseDto);
        when(createCategoriaUseCase.execute(any(Categoria.class))).thenReturn(categoriaMock);

        // Act
        categoriaService.listarCategorias();
        categoriaService.buscarCategoriaPorId(1);
        categoriaService.criarCategoria(categoriaCreateDto);

        // Assert
        verify(listAllCategoriasUseCase, times(1)).execute();
        verify(findCategoriaByIdUseCase, times(1)).execute(1);
        verify(createCategoriaUseCase, times(1)).execute(any(Categoria.class));
    }

    @Test
    @DisplayName("Deve verificar injeção de dependências")
    void deveVerificarInjecaoDeDependencias() {
        // Assert
        assertNotNull(categoriaService);
        assertNotNull(findCategoriaByIdUseCase);
        assertNotNull(listAllCategoriasUseCase);
        assertNotNull(createCategoriaUseCase);
    }
}
