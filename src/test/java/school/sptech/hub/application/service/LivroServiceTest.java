package school.sptech.hub.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import school.sptech.hub.application.usecases.livro.*;
import school.sptech.hub.domain.dto.livro.LivroComSinopseResponseDto;
import school.sptech.hub.domain.dto.livro.LivroCreateDto;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.dto.livro.LivroUpdateDto;

import java.time.Year;
import java.util.Arrays;
import java.util.List;

class LivroServiceTest {

    @Mock
    private CreateLivroUseCase createLivroUseCase;

    @Mock
    private FindLivroByIdUseCase findLivroByIdUseCase;

    @Mock
    private ListAllLivrosUseCase listAllLivrosUseCase;

    @Mock
    private FindLivroWithSinopseUseCase findLivroWithSinopseUseCase;

    @Mock
    private UpdateLivroUseCase updateLivroUseCase;

    @Mock
    private DeleteLivroUseCase deleteLivroUseCase;

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
            listAllLivrosUseCase,
            findLivroWithSinopseUseCase,
            updateLivroUseCase,
            deleteLivroUseCase
        );

        // Setup test data
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
        // Given
        when(createLivroUseCase.execute(livroCreateDto)).thenReturn(livroResponseDto);

        // When
        LivroResponseDto result = livroService.createNewLivro(livroCreateDto);

        // Then
        assertNotNull(result);
        assertEquals(livroResponseDto.getId(), result.getId());
        assertEquals(livroResponseDto.getTitulo(), result.getTitulo());
        verify(createLivroUseCase).execute(livroCreateDto);
    }

    @Test
    void testBuscarLivroPorId() {
        // Given
        Integer id = 1;
        when(findLivroByIdUseCase.execute(id)).thenReturn(livroResponseDto);

        // When
        LivroResponseDto result = livroService.buscarLivroPorId(id);

        // Then
        assertNotNull(result);
        assertEquals(livroResponseDto.getId(), result.getId());
        verify(findLivroByIdUseCase).execute(id);
    }

    @Test
    void testListarLivros() {
        // Given
        LivroResponseDto livro2 = new LivroResponseDto();
        livro2.setId(2);
        livro2.setTitulo("O Cortiço");

        List<LivroResponseDto> livros = Arrays.asList(livroResponseDto, livro2);
        when(listAllLivrosUseCase.execute()).thenReturn(livros);

        // When
        List<LivroResponseDto> result = livroService.listarLivros();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Dom Casmurro", result.get(0).getTitulo());
        assertEquals("O Cortiço", result.get(1).getTitulo());
        verify(listAllLivrosUseCase).execute();
    }

    @Test
    void testBuscarLivroPorIdComSinopse() {
        // Given
        Integer id = 1;
        LivroComSinopseResponseDto livroComSinopse = new LivroComSinopseResponseDto();
        livroComSinopse.setId(1);
        livroComSinopse.setTitulo("Dom Casmurro");
        livroComSinopse.setSinopse("Uma obra-prima da literatura brasileira");

        when(findLivroWithSinopseUseCase.execute(id)).thenReturn(livroComSinopse);

        // When
        LivroComSinopseResponseDto result = livroService.buscarLivroPorIdComSinopse(id);

        // Then
        assertNotNull(result);
        assertEquals(livroComSinopse.getId(), result.getId());
        assertEquals(livroComSinopse.getTitulo(), result.getTitulo());
        assertEquals(livroComSinopse.getSinopse(), result.getSinopse());
        verify(findLivroWithSinopseUseCase).execute(id);
    }

    @Test
    void testAtualizarLivro() {
        // Given
        Integer id = 1;
        LivroResponseDto livroAtualizado = new LivroResponseDto();
        livroAtualizado.setId(1);
        livroAtualizado.setTitulo("Dom Casmurro - Edição Revisada");
        livroAtualizado.setPreco(35.90);

        when(updateLivroUseCase.execute(id, livroUpdateDto)).thenReturn(livroAtualizado);

        // When
        LivroResponseDto result = livroService.atualizarLivro(id, livroUpdateDto);

        // Then
        assertNotNull(result);
        assertEquals(livroAtualizado.getId(), result.getId());
        assertEquals(livroAtualizado.getTitulo(), result.getTitulo());
        assertEquals(livroAtualizado.getPreco(), result.getPreco());
        verify(updateLivroUseCase).execute(id, livroUpdateDto);
    }

    @Test
    void testDeletarLivro() {
        // Given
        Integer id = 1;
        when(deleteLivroUseCase.execute(id)).thenReturn(livroResponseDto);

        // When
        LivroResponseDto result = livroService.deletarLivro(id);

        // Then
        assertNotNull(result);
        assertEquals(livroResponseDto.getId(), result.getId());
        verify(deleteLivroUseCase).execute(id);
    }
}
