package school.sptech.hub.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import school.sptech.hub.application.usecases.livro.*;
import school.sptech.hub.domain.dto.livro.LivroCreateDto;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.dto.livro.LivroUpdateDto;
import school.sptech.hub.domain.dto.livro.LivroPaginatedResponseDto;

import java.util.Arrays;
import java.util.List;

class LivroServiceTest {

    @Mock
    private CreateLivroUseCase createLivroUseCase;

    @Mock
    private FindLivroByIdUseCase findLivroByIdUseCase;

    @Mock
    private ListLivrosPaginatedUseCase listLivrosPaginatedUseCase;

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

    @Mock
    private UpdateLivroSinopseUseCase updateLivroSinopseUseCase;

    @Mock
    private ListLivrosElasticSearchPaginatedUseCase listLivrosElasticSearchPaginatedUseCase;

    private LivroService livroService;
    private LivroCreateDto livroCreateDto;
    private LivroUpdateDto livroUpdateDto;
    private LivroResponseDto livroResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        livroService = new LivroService(
            createLivroUseCase,
            findLivroByIdUseCase,
            listLivrosPaginatedUseCase,
            updateLivroUseCase,
            deleteLivroUseCase,
            uploadImageUseCase,
            findLivrosByAcabamentoUseCase,
            findLivrosByConservacaoUseCase,
            findLivrosByCategoriaUseCase,
            listAllCategoriesUseCase,
            listRecommendedLivrosUseCase,
            listRecentLivrosUseCase,
            updateLivroSinopseUseCase,
            listLivrosElasticSearchPaginatedUseCase
        );

        livroCreateDto = new LivroCreateDto();
        livroCreateDto.setTitulo("Dom Casmurro");
        livroCreateDto.setAutor("Machado de Assis");
        livroCreateDto.setIsbn("978-85-359-0277-5");

        livroUpdateDto = new LivroUpdateDto();
        livroUpdateDto.setTitulo("Dom Casmurro - Edição Revisada");
        livroUpdateDto.setPreco(35.90);

        livroResponseDto = new LivroResponseDto();
        livroResponseDto.setId(1);
        livroResponseDto.setTitulo("Dom Casmurro");
        livroResponseDto.setAutor("Machado de Assis");
        livroResponseDto.setIsbn("978-85-359-0277-5");
        livroResponseDto.setAnoPublicacao(2008);
    }

    @Test
    void testCreateNewLivro() {
        when(createLivroUseCase.execute(livroCreateDto)).thenReturn(livroResponseDto);

        LivroResponseDto result = livroService.createNewLivro(livroCreateDto);

        assertNotNull(result);
        assertEquals(livroResponseDto.getId(), result.getId());
        assertEquals(livroResponseDto.getTitulo(), result.getTitulo());
        verify(createLivroUseCase).execute(livroCreateDto);
    }

    @Test
    void testBuscarLivroPorId() {
        Integer id = 1;
        when(findLivroByIdUseCase.execute(id)).thenReturn(livroResponseDto);

        LivroResponseDto result = livroService.buscarLivroPorId(id);

        assertNotNull(result);
        assertEquals(livroResponseDto.getId(), result.getId());
        verify(findLivroByIdUseCase).execute(id);
    }

    @Test
    void testListarLivros() {
        LivroResponseDto livro2 = new LivroResponseDto();
        livro2.setId(2);
        livro2.setTitulo("O Cortiço");

        List<LivroResponseDto> livros = Arrays.asList(livroResponseDto, livro2);
        LivroPaginatedResponseDto paginatedResponse = new LivroPaginatedResponseDto(livros, 1, 2, 0);
        when(listLivrosPaginatedUseCase.execute(0, 10)).thenReturn(paginatedResponse);

        // When
        List<LivroResponseDto> result = livroService.listarLivrosPaginado(0, 10).getLivros();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Dom Casmurro", result.get(0).getTitulo());
        assertEquals("O Cortiço", result.get(1).getTitulo());
        verify(listLivrosPaginatedUseCase).execute(0, 10);
    }

    @Test
    void testAtualizarLivro() {
        Integer id = 1;
        LivroResponseDto livroAtualizado = new LivroResponseDto();
        livroAtualizado.setId(1);
        livroAtualizado.setTitulo("Dom Casmurro - Edição Revisada");
        livroAtualizado.setPreco(35.90);

        when(updateLivroUseCase.execute(id, livroUpdateDto)).thenReturn(livroAtualizado);

        LivroResponseDto result = livroService.atualizarLivro(id, livroUpdateDto);

        assertNotNull(result);
        assertEquals(livroAtualizado.getId(), result.getId());
        assertEquals(livroAtualizado.getTitulo(), result.getTitulo());
        assertEquals(livroAtualizado.getPreco(), result.getPreco());
        verify(updateLivroUseCase).execute(id, livroUpdateDto);
    }

    @Test
    void testDeletarLivro() {
        Integer id = 1;
        when(deleteLivroUseCase.execute(id)).thenReturn(livroResponseDto);

        LivroResponseDto result = livroService.deletarLivro(id);

        assertNotNull(result);
        assertEquals(livroResponseDto.getId(), result.getId());
        verify(deleteLivroUseCase).execute(id);
    }
}

