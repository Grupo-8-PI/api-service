package school.sptech.hub.application.usecases.livro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import school.sptech.hub.application.exceptions.AcabamentoExceptions.AcabamentoNaoEncontradoException;
import school.sptech.hub.application.exceptions.CategoriaExceptions.CategoriaNaoEncontradaException;
import school.sptech.hub.application.exceptions.ConservacaoExceptions.ConservacaoNaoEncontradaException;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroJaExisteException;
import school.sptech.hub.application.gateways.acabamento.AcabamentoGateway;
import school.sptech.hub.application.gateways.categoria.CategoriaGateway;
import school.sptech.hub.application.gateways.conservacao.ConservacaoGateway;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.dto.livro.LivroCreateDto;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.entity.Acabamento;
import school.sptech.hub.domain.entity.Categoria;
import school.sptech.hub.domain.entity.Conservacao;
import school.sptech.hub.domain.entity.Livro;

import java.time.Year;
import java.util.Optional;

class CreateLivroUseCaseTest {

    @Mock
    private LivroGateway livroGateway;

    @Mock
    private AcabamentoGateway acabamentoGateway;

    @Mock
    private CategoriaGateway categoriaGateway;

    @Mock
    private ConservacaoGateway conservacaoGateway;

    private CreateLivroUseCase createLivroUseCase;
    private LivroCreateDto livroCreateDto;
    private Acabamento acabamento;
    private Categoria categoria;
    private Conservacao conservacao;
    private Livro livro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createLivroUseCase = new CreateLivroUseCase(livroGateway, acabamentoGateway, categoriaGateway, conservacaoGateway);

        // Setup test data
        livroCreateDto = new LivroCreateDto();
        livroCreateDto.setIsbn("978-85-359-0277-5");
        livroCreateDto.setTitulo("Dom Casmurro");
        livroCreateDto.setAutor("Machado de Assis");
        livroCreateDto.setEditora("Companhia das Letras");
        livroCreateDto.setAnoPublicacao(Year.of(2008));
        livroCreateDto.setPaginas(256);
        livroCreateDto.setAcabamentoId(1);
        livroCreateDto.setConservacaoId(2);
        livroCreateDto.setCapa("https://example.com/capa.jpg");
        livroCreateDto.setPreco(29.90);
        livroCreateDto.setCategoriaId(3);

        acabamento = new Acabamento(1, "Brochura");
        categoria = new Categoria(3, "Romance");
        conservacao = new Conservacao(2, "OTIMO");

        livro = new Livro(
            1,
            "Dom Casmurro",
            "978-85-359-0277-5",
            "Machado de Assis",
            "Companhia das Letras",
            Year.of(2008),
            256,
            acabamento,
            conservacao,
            "https://example.com/capa.jpg",
            29.90,
            categoria
        );
    }

    @Test
    void testExecute_Success() {
        // Given
        when(acabamentoGateway.findById(1)).thenReturn(Optional.of(acabamento));
        when(categoriaGateway.findById(3)).thenReturn(Optional.of(categoria));
        when(conservacaoGateway.findById(2)).thenReturn(Optional.of(conservacao));
        when(livroGateway.findByIsbn("978-85-359-0277-5")).thenReturn(Optional.empty());
        when(livroGateway.createLivro(any(Livro.class))).thenReturn(Optional.of(livro));

        // When
        LivroResponseDto result = createLivroUseCase.execute(livroCreateDto);

        // Then
        assertNotNull(result);
        assertEquals("Dom Casmurro", result.getTitulo());
        assertEquals("978-85-359-0277-5", result.getIsbn());
        assertEquals("Machado de Assis", result.getAutor());
        assertEquals(1, result.getAcabamentoId());
        assertEquals("Brochura", result.getTipoAcabamento());
        assertEquals(3, result.getCategoriaId());
        assertEquals("Romance", result.getNomeCategoria());

        verify(acabamentoGateway).findById(1);
        verify(categoriaGateway).findById(3);
        verify(conservacaoGateway).findById(2);
        verify(livroGateway).findByIsbn("978-85-359-0277-5");
        verify(livroGateway).createLivro(any(Livro.class));
    }

    @Test
    void testExecute_AcabamentoNaoEncontrado() {
        // Given
        when(acabamentoGateway.findById(1)).thenReturn(Optional.empty());

        // When & Then
        AcabamentoNaoEncontradoException exception = assertThrows(
            AcabamentoNaoEncontradoException.class,
            () -> createLivroUseCase.execute(livroCreateDto)
        );

        assertEquals("Acabamento não encontrado com ID: 1", exception.getMessage());
        verify(acabamentoGateway).findById(1);
        verifyNoInteractions(categoriaGateway, conservacaoGateway, livroGateway);
    }

    @Test
    void testExecute_CategoriaNaoEncontrada() {
        // Given
        when(acabamentoGateway.findById(1)).thenReturn(Optional.of(acabamento));
        when(categoriaGateway.findById(3)).thenReturn(Optional.empty());

        // When & Then
        CategoriaNaoEncontradaException exception = assertThrows(
            CategoriaNaoEncontradaException.class,
            () -> createLivroUseCase.execute(livroCreateDto)
        );

        assertEquals("Categoria não encontrada com ID: 3", exception.getMessage());
        verify(acabamentoGateway).findById(1);
        verify(categoriaGateway).findById(3);
        verifyNoInteractions(conservacaoGateway, livroGateway);
    }

    @Test
    void testExecute_ConservacaoNaoEncontrada() {
        // Given
        when(acabamentoGateway.findById(1)).thenReturn(Optional.of(acabamento));
        when(categoriaGateway.findById(3)).thenReturn(Optional.of(categoria));
        when(conservacaoGateway.findById(2)).thenReturn(Optional.empty());

        // When & Then
        ConservacaoNaoEncontradaException exception = assertThrows(
            ConservacaoNaoEncontradaException.class,
            () -> createLivroUseCase.execute(livroCreateDto)
        );

        assertEquals("Conservação não encontrada com ID: 2", exception.getMessage());
        verify(acabamentoGateway).findById(1);
        verify(categoriaGateway).findById(3);
        verify(conservacaoGateway).findById(2);
        verifyNoInteractions(livroGateway);
    }

    @Test
    void testExecute_LivroJaExiste() {
        // Given
        when(acabamentoGateway.findById(1)).thenReturn(Optional.of(acabamento));
        when(categoriaGateway.findById(3)).thenReturn(Optional.of(categoria));
        when(conservacaoGateway.findById(2)).thenReturn(Optional.of(conservacao));
        when(livroGateway.findByIsbn("978-85-359-0277-5")).thenReturn(Optional.of(livro));

        // When & Then
        LivroJaExisteException exception = assertThrows(
            LivroJaExisteException.class,
            () -> createLivroUseCase.execute(livroCreateDto)
        );

        assertEquals("Já existe um livro cadastrado com este ISBN.", exception.getMessage());
        verify(livroGateway).findByIsbn("978-85-359-0277-5");
        verify(livroGateway, never()).createLivro(any());
    }

    @Test
    void testExecute_ErroAoCriarLivro() {
        // Given
        when(acabamentoGateway.findById(1)).thenReturn(Optional.of(acabamento));
        when(categoriaGateway.findById(3)).thenReturn(Optional.of(categoria));
        when(conservacaoGateway.findById(2)).thenReturn(Optional.of(conservacao));
        when(livroGateway.findByIsbn("978-85-359-0277-5")).thenReturn(Optional.empty());
        when(livroGateway.createLivro(any(Livro.class))).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> createLivroUseCase.execute(livroCreateDto));
        verify(livroGateway).createLivro(any(Livro.class));
    }
}
