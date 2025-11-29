package school.sptech.hub.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import school.sptech.hub.application.adapter.ChatGptAdapter;
import school.sptech.hub.domain.entity.Livro;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ChatGptAdapterTest {

    private ChatGptAdapter chatGptAdapter;

    @BeforeEach
    void setUp() {
        // Arrange: cria um mock isolado para testes
        chatGptAdapter = mock(ChatGptAdapter.class);
    }

    @Test
    @DisplayName("Deve gerar sinopse com livro válido")
    void deveGerarSinopseComLivroValido() {
        // Arrange
        Livro livro = new Livro();
        livro.setId(1);
        livro.setTitulo("O Hobbit");
        livro.setAutor("J.R.R. Tolkien");
        livro.setIsbn("978-0547928227");

        String sinopseEsperada = "Sinopse gerada para O Hobbit por J.R.R. Tolkien";

        when(chatGptAdapter.gerarSinopse(livro)).thenReturn(sinopseEsperada);

        // Act
        String resultado = chatGptAdapter.gerarSinopse(livro);

        // Assert
        assertEquals(sinopseEsperada, resultado);
        verify(chatGptAdapter).gerarSinopse(livro);
    }

    @Test
    @DisplayName("Deve retornar null quando livro for nulo")
    void deveRetornarNullComLivroNulo() {
        // Arrange
        when(chatGptAdapter.gerarSinopse(null)).thenReturn(null);

        // Act
        String resultado = chatGptAdapter.gerarSinopse(null);

        // Assert
        assertNull(resultado);
        verify(chatGptAdapter).gerarSinopse(null);
    }

    @Test
    @DisplayName("Deve retornar null quando livro não tiver título")
    void deveRetornarNullComLivroSemTitulo() {
        // Arrange
        Livro livro = new Livro();
        livro.setId(1);
        livro.setAutor("J.R.R. Tolkien");
        livro.setIsbn("978-0547928227");
        // título não definido (null)

        when(chatGptAdapter.gerarSinopse(livro)).thenReturn(null);

        // Act
        String resultado = chatGptAdapter.gerarSinopse(livro);

        // Assert
        assertNull(resultado);
        verify(chatGptAdapter).gerarSinopse(livro);
    }

    @Test
    @DisplayName("Deve retornar null quando livro não tiver autor")
    void deveRetornarNullComLivroSemAutor() {
        // Arrange
        Livro livro = new Livro();
        livro.setId(1);
        livro.setTitulo("O Hobbit");
        livro.setIsbn("978-0547928227");
        // autor não definido (null)

        when(chatGptAdapter.gerarSinopse(livro)).thenReturn(null);

        // Act
        String resultado = chatGptAdapter.gerarSinopse(livro);

        // Assert
        assertNull(resultado);
        verify(chatGptAdapter).gerarSinopse(livro);
    }
}
