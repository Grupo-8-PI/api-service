package school.sptech.hub.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import school.sptech.hub.application.adapter.ChatGptAdapter;
import school.sptech.hub.application.service.LivroService;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.domain.dto.livro.LivroComSinopseResponseDto;
import school.sptech.hub.domain.dto.livro.LivroCreateDto;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.infraestructure.persistance.AcabamentoRepository;
import school.sptech.hub.infraestructure.persistance.CategoriaRepository;
import school.sptech.hub.infraestructure.persistance.ConservacaoRepository;
import school.sptech.hub.infraestructure.persistance.LivroRepository;

import java.util.List;
import java.util.Optional;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LivroServiceTest {

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private AcabamentoRepository acabamentoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ConservacaoRepository conservacaoRepository;

    @Mock
    private ChatGptAdapter chatGptAdapter;

    private Livro livro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        livro = new Livro();
        livro.setId(1);
        livro.setTitulo("Dom Casmurro");
        livro.setAutor("Machado de Assis");
        livro.setAnoPublicacao(Year.of(1899));
    }

    @Test
    void deveCriarNovoLivro() {
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        LivroCreateDto createDto = new LivroCreateDto();
        Livro livroCriado = livroService.createNewLivro(createDto);

        assertNotNull(livroCriado);
        assertEquals("Dom Casmurro", livroCriado.getTitulo());
    }

    @Test
    void deveListarTodosLivros() {
        when(livroRepository.findAll()).thenReturn(List.of(livro));
        List<LivroResponseDto> livros = livroService.listarLivros();

        assertEquals(1, livros.size());
        assertEquals("Dom Casmurro", livros.getFirst().getTitulo());
    }

    @Test
    void deveBuscarLivroPorIdComSinopse() {
        when(livroRepository.findById(1)).thenReturn(Optional.of(livro));
        when(chatGptAdapter.gerarSinopse(any(), any())).thenReturn("Sinopse gerada");

        LivroComSinopseResponseDto resultado = livroService.buscarLivroPorIdComSinopse(1);

        assertNotNull(resultado);
        assertEquals("Sinopse gerada", resultado.getSinopse());
    }

    @Test
    void deveBuscarLivroPorId() {
        when(livroRepository.findById(1)).thenReturn(Optional.of(livro));

        LivroResponseDto dto = livroService.buscarLivroPorId(1);

        assertNotNull(dto);
        assertEquals("Dom Casmurro", dto.getTitulo());
    }

    @Test
    void deveAtualizarLivroExistente() {
        Livro novoLivro = new Livro();
        novoLivro.setTitulo("Atualizado");

        when(livroRepository.findById(1)).thenReturn(Optional.of(livro));
        when(livroRepository.save(any())).thenReturn(novoLivro);

        Livro resultado = livroService.atualizarLivro(1, novoLivro);

        assertNotNull(resultado);
        assertEquals("Atualizado", resultado.getTitulo());
    }

    @Test
    void deveDeletarLivro() {
        when(livroRepository.findById(1)).thenReturn(Optional.of(livro));
        doNothing().when(livroRepository).deleteById(1);

        Livro resultado = livroService.deletarLivro(1);

        assertNotNull(resultado);
        verify(livroRepository).deleteById(1);
    }

    @Test
    void deveLancarExcecao_QuandoLivroNaoExiste_noBuscarPorId() {
        when(livroRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(LivroNaoEncontradoException.class, () -> livroService.buscarLivroPorId(2));
    }

    @Test
    void deveLancarExcecao_QuandoLivroNaoExiste_noBuscarPorIdComSinopse() {
        when(livroRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(LivroNaoEncontradoException.class, () -> livroService.buscarLivroPorIdComSinopse(99));
    }
}
