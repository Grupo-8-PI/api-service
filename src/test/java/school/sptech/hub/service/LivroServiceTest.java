package school.sptech.hub.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import school.sptech.hub.application.service.LivroService;
import school.sptech.hub.application.usecases.livro.*;
import school.sptech.hub.domain.dto.livro.LivroCreateDto;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.dto.livro.LivroUpdateDto;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock
    private CreateLivroUseCase createLivroUseCase;

    @Mock
    private FindLivroByIdUseCase findLivroByIdUseCase;

    @Mock
    private UpdateLivroUseCase updateLivroUseCase;

    @Mock
    private DeleteLivroUseCase deleteLivroUseCase;

    @Mock
    private UploadImageUseCase uploadImageUseCase;

    @Mock
    private FindLivrosByAcabamentoUseCase findLivrosByAcabamentoUseCase;

    @Mock
    private FindLivrosByConservacaoUseCase findLivrosByConservacaoUseCase;

    @Mock
    private FindLivrosByCategoriaUseCase findLivrosByCategoriaUseCase;

    @Mock
    private ListAllCategoriesUseCase listAllCategoriesUseCase;

    @Mock
    private ListRecommendedLivrosUseCase listRecommendedLivrosUseCase;

    @Mock
    private ListRecentLivrosUseCase listRecentLivrosUseCase;

    @InjectMocks
    private LivroService livroService;

    private LivroCreateDto livroCreateDto;
    private LivroUpdateDto livroUpdateDto;
    private LivroResponseDto livroResponseDto;

    @BeforeEach
    void setUp() {
        livroCreateDto = new LivroCreateDto();
        livroCreateDto.setTitulo("Dom Casmurro");
        livroCreateDto.setAutor("Machado de Assis");
        livroCreateDto.setIsbn("978-85-359-0277-5");
        livroCreateDto.setEditora("Ática");
        livroCreateDto.setAnoPublicacao(Year.of(2008));
        livroCreateDto.setPaginas(256);

        livroUpdateDto = new LivroUpdateDto();
        livroUpdateDto.setTitulo("Dom Casmurro - Edição Revisada");
        livroUpdateDto.setPreco(35.90);

        livroResponseDto = new LivroResponseDto();
        livroResponseDto.setId(1);
        livroResponseDto.setTitulo("Dom Casmurro");
        livroResponseDto.setAutor("Machado de Assis");
        livroResponseDto.setIsbn("978-85-359-0277-5");
        livroResponseDto.setEditora("Ática");
        livroResponseDto.setAnoPublicacao(2008);
        livroResponseDto.setPaginas(256);
        livroResponseDto.setPreco(29.90);
    }

    // ===== TESTES PARA CREATE LIVRO =====

    @Test
    @DisplayName("Deve criar livro com sucesso")
    void deveCriarLivroComSucesso() {
        // Arrange
        when(createLivroUseCase.execute(livroCreateDto)).thenReturn(livroResponseDto);

        // Act
        LivroResponseDto result = livroService.createNewLivro(livroCreateDto);

        // Assert
        assertNotNull(result);
        assertEquals(livroResponseDto.getId(), result.getId());
        assertEquals(livroResponseDto.getTitulo(), result.getTitulo());
        assertEquals(livroResponseDto.getAutor(), result.getAutor());
        assertEquals(livroResponseDto.getIsbn(), result.getIsbn());
        verify(createLivroUseCase, times(1)).execute(livroCreateDto);
    }

    // ===== TESTES PARA BUSCAR LIVRO POR ID =====

    @Test
    @DisplayName("Deve buscar livro por ID com sucesso")
    void deveBuscarLivroPorIdComSucesso() {
        // Arrange
        Integer id = 1;
        when(findLivroByIdUseCase.execute(id)).thenReturn(livroResponseDto);

        // Act
        LivroResponseDto result = livroService.buscarLivroPorId(id);

        // Assert
        assertNotNull(result);
        assertEquals(livroResponseDto.getId(), result.getId());
        assertEquals(livroResponseDto.getTitulo(), result.getTitulo());
        verify(findLivroByIdUseCase, times(1)).execute(id);
    }

    // ===== TESTES PARA ATUALIZAR LIVRO =====

    @Test
    @DisplayName("Deve atualizar livro com sucesso")
    void deveAtualizarLivroComSucesso() {
        // Arrange
        Integer id = 1;
        LivroResponseDto livroAtualizado = new LivroResponseDto();
        livroAtualizado.setId(1);
        livroAtualizado.setTitulo("Dom Casmurro - Edição Revisada");
        livroAtualizado.setAutor("Machado de Assis");
        livroAtualizado.setPreco(35.90);

        when(updateLivroUseCase.execute(id, livroUpdateDto)).thenReturn(livroAtualizado);

        // Act
        LivroResponseDto result = livroService.atualizarLivro(id, livroUpdateDto);

        // Assert
        assertNotNull(result);
        assertEquals(livroAtualizado.getId(), result.getId());
        assertEquals(livroAtualizado.getTitulo(), result.getTitulo());
        assertEquals(livroAtualizado.getPreco(), result.getPreco());
        verify(updateLivroUseCase, times(1)).execute(id, livroUpdateDto);
    }

    // ===== TESTES PARA DELETAR LIVRO =====

    @Test
    @DisplayName("Deve deletar livro com sucesso")
    void deveDeletarLivroComSucesso() {
        // Arrange
        Integer id = 1;
        when(deleteLivroUseCase.execute(id)).thenReturn(livroResponseDto);

        // Act
        LivroResponseDto result = livroService.deletarLivro(id);

        // Assert
        assertNotNull(result);
        assertEquals(livroResponseDto.getId(), result.getId());
        assertEquals(livroResponseDto.getTitulo(), result.getTitulo());
        verify(deleteLivroUseCase, times(1)).execute(id);
    }

    // ===== TESTES PARA UPLOAD DE IMAGEM =====

    @Test
    @DisplayName("Deve atualizar imagem do livro com sucesso")
    void deveAtualizarImagemDoLivroComSucesso() {
        // Arrange
        Integer id = 1;
        byte[] conteudoArquivo = "imagem-teste".getBytes();
        String nomeArquivo = "capa.jpg";
        String contentType = "image/jpeg";

        LivroResponseDto livroComImagem = new LivroResponseDto();
        livroComImagem.setId(1);
        livroComImagem.setTitulo("Dom Casmurro");
        livroComImagem.setCapa("https://exemplo.com/capa.jpg");

        when(uploadImageUseCase.execute(id, conteudoArquivo, nomeArquivo, contentType))
                .thenReturn(livroComImagem);

        // Act
        LivroResponseDto result = livroService.atualizarImagemLivro(id, conteudoArquivo, nomeArquivo, contentType);

        // Assert
        assertNotNull(result);
        assertEquals(livroComImagem.getId(), result.getId());
        assertEquals(livroComImagem.getCapa(), result.getCapa());
        verify(uploadImageUseCase, times(1)).execute(id, conteudoArquivo, nomeArquivo, contentType);
    }

    // ===== TESTES PARA BUSCAR POR ACABAMENTO =====

    @Test
    @DisplayName("Deve buscar livros por acabamento com sucesso")
    void deveBuscarLivrosPorAcabamentoComSucesso() {
        // Arrange
        Integer acabamentoId = 1;
        List<LivroResponseDto> livros = Collections.singletonList(livroResponseDto);
        when(findLivrosByAcabamentoUseCase.execute(acabamentoId)).thenReturn(livros);

        // Act
        List<LivroResponseDto> result = livroService.buscarLivrosPorAcabamento(acabamentoId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(livroResponseDto.getTitulo(), result.get(0).getTitulo());
        verify(findLivrosByAcabamentoUseCase, times(1)).execute(acabamentoId);
    }

    // ===== TESTES PARA BUSCAR POR CONSERVAÇÃO =====

    @Test
    @DisplayName("Deve buscar livros por conservação com sucesso")
    void deveBuscarLivrosPorConservacaoComSucesso() {
        // Arrange
        Integer conservacaoId = 2;
        List<LivroResponseDto> livros = Collections.singletonList(livroResponseDto);
        when(findLivrosByConservacaoUseCase.execute(conservacaoId)).thenReturn(livros);

        // Act
        List<LivroResponseDto> result = livroService.buscarLivrosPorConservacao(conservacaoId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(livroResponseDto.getTitulo(), result.get(0).getTitulo());
        verify(findLivrosByConservacaoUseCase, times(1)).execute(conservacaoId);
    }

    // ===== TESTES PARA BUSCAR POR CATEGORIA =====

    @Test
    @DisplayName("Deve buscar livros por categoria com sucesso")
    void deveBuscarLivrosPorCategoriaComSucesso() {
        // Arrange
        Integer categoriaId = 1;
        List<LivroResponseDto> livros = Collections.singletonList(livroResponseDto);
        when(findLivrosByCategoriaUseCase.execute(categoriaId)).thenReturn(livros);

        // Act
        List<LivroResponseDto> result = livroService.buscarLivrosPorCategoria(categoriaId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(livroResponseDto.getTitulo(), result.get(0).getTitulo());
        verify(findLivrosByCategoriaUseCase, times(1)).execute(categoriaId);
    }

    // ===== TESTES PARA LISTAR CATEGORIAS =====

    @Test
    @DisplayName("Deve listar categorias com sucesso")
    void deveListarCategoriasComSucesso() {
        // Arrange
        List<String> categorias = Arrays.asList("Ficção", "Romance", "Literatura Brasileira");
        when(listAllCategoriesUseCase.execute()).thenReturn(categorias);

        // Act
        List<String> result = livroService.listarCategorias();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains("Ficção"));
        assertTrue(result.contains("Romance"));
        assertTrue(result.contains("Literatura Brasileira"));
        verify(listAllCategoriesUseCase, times(1)).execute();
    }

    // ===== TESTES PARA LIVROS RECOMENDADOS =====

    @Test
    @DisplayName("Deve listar livros recomendados com sucesso")
    void deveListarLivrosRecomendadosComSucesso() {
        // Arrange
        List<LivroResponseDto> livrosRecomendados = Collections.singletonList(livroResponseDto);
        when(listRecommendedLivrosUseCase.execute()).thenReturn(livrosRecomendados);

        // Act
        List<LivroResponseDto> result = livroService.listarLivrosRecomendados();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(livroResponseDto.getTitulo(), result.get(0).getTitulo());
        verify(listRecommendedLivrosUseCase, times(1)).execute();
    }

    // ===== TESTES PARA LIVROS RECENTES =====

    @Test
    @DisplayName("Deve listar livros recentes com sucesso")
    void deveListarLivrosRecentesComSucesso() {
        // Arrange
        List<LivroResponseDto> livrosRecentes = Collections.singletonList(livroResponseDto);
        when(listRecentLivrosUseCase.execute()).thenReturn(livrosRecentes);

        // Act
        List<LivroResponseDto> result = livroService.listarLivrosRecentes();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(livroResponseDto.getTitulo(), result.get(0).getTitulo());
        verify(listRecentLivrosUseCase, times(1)).execute();
    }
}
