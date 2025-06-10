package school.sptech.hub.adapter.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import school.sptech.hub.adapter.ChatGptAdapter;

import static org.junit.jupiter.api.Assertions.*;

class ChatGptAdapterImplTest {

    private ChatGptAdapter adapter;

    @BeforeEach
    void setUp() {
        // Arrange: instanciando a implementação real
        adapter = new ChatGptAdapterImpl();
    }

    @Test
    @DisplayName("Deve gerar sinopse válida com título e autor válidos")
    void deveGerarSinopseValida() {
        // Arrange
        String titulo = "1984";
        String autor = "George Orwell";

        // Act
        String resultado = adapter.gerarSinopse(titulo, autor);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.contains("1984"));
        assertTrue(resultado.contains("George Orwell"));
        assertEquals("Sinopse gerada para o livro '1984' de George Orwell.", resultado);
    }

    @Test
    @DisplayName("Deve lidar com título nulo")
    void deveGerarSinopseComTituloNulo() {
        // Arrange
        String autor = "George Orwell";

        // Act
        String resultado = adapter.gerarSinopse(null, autor);

        // Assert
        assertEquals("Sinopse gerada para o livro 'null' de George Orwell.", resultado);
    }

    @Test
    @DisplayName("Deve lidar com autor vazio")
    void deveGerarSinopseComAutorVazio() {
        // Arrange
        String titulo = "A Revolução dos Bichos";

        // Act
        String resultado = adapter.gerarSinopse(titulo, "");

        // Assert
        assertEquals("Sinopse gerada para o livro 'A Revolução dos Bichos' de .", resultado);
    }

    @Test
    @DisplayName("Deve lidar com ambos os parâmetros vazios")
    void deveGerarSinopseComTituloEAutorVazios() {
        // Arrange & Act
        String resultado = adapter.gerarSinopse("", "");

        // Assert
        assertEquals("Sinopse gerada para o livro '' de .", resultado);
    }
}
