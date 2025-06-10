package school.sptech.hub.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    @DisplayName("Deve gerar sinopse com título e autor válidos")
    void deveGerarSinopseComTituloEAutorValidos() {
        // Arrange
        String titulo = "O Hobbit";
        String autor = "J.R.R. Tolkien";
        String sinopseEsperada = "Sinopse gerada para O Hobbit por J.R.R. Tolkien";

        when(chatGptAdapter.gerarSinopse(titulo, autor)).thenReturn(sinopseEsperada);

        // Act
        String resultado = chatGptAdapter.gerarSinopse(titulo, autor);

        // Assert
        assertEquals(sinopseEsperada, resultado);
    }

    @Test
    @DisplayName("Deve retornar null quando título for nulo")
    void deveRetornarNullComTituloNulo() {
        // Arrange
        String autor = "J.R.R. Tolkien";

        when(chatGptAdapter.gerarSinopse(null, autor)).thenReturn(null);

        // Act
        String resultado = chatGptAdapter.gerarSinopse(null, autor);

        // Assert
        assertNull(resultado);
    }

    @Test
    @DisplayName("Deve retornar null quando autor for vazio")
    void deveRetornarNullComAutorVazio() {
        // Arrange
        String titulo = "O Hobbit";
        String autor = "";

        when(chatGptAdapter.gerarSinopse(titulo, autor)).thenReturn(null);

        // Act
        String resultado = chatGptAdapter.gerarSinopse(titulo, autor);

        // Assert
        assertNull(resultado);
    }
}
