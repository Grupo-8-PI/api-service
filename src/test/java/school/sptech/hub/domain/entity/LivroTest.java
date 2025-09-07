package school.sptech.hub.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Year;

class LivroTest {

    private Livro livro;
    private Acabamento acabamento;
    private Categoria categoria;
    private Conservacao conservacao;

    @BeforeEach
    void setUp() {
        // Configurar entidades relacionadas
        acabamento = new Acabamento(1, "Brochura");
        categoria = new Categoria(1, "Romance");
        conservacao = new Conservacao(1, "OTIMO");

        // Configurar livro válido
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
    void testValidLivroForCreation() {
        assertTrue(livro.isValidForCreation());
    }

    @Test
    void testValidateBusinessRules_Success() {
        assertDoesNotThrow(() -> livro.validateBusinessRules());
    }

    @Test
    void testValidateBusinessRules_InvalidISBN() {
        livro.setIsbn("invalid-isbn");

        assertThrows(IllegalArgumentException.class, () -> {
            livro.validateBusinessRules();
        });
    }

    @Test
    void testValidateBusinessRules_NullTitulo() {
        livro.setTitulo(null);

        assertThrows(IllegalArgumentException.class, () -> {
            livro.validateBusinessRules();
        });
    }

    @Test
    void testValidateBusinessRules_EmptyTitulo() {
        livro.setTitulo("");

        assertThrows(IllegalArgumentException.class, () -> {
            livro.validateBusinessRules();
        });
    }

    @Test
    void testValidateBusinessRules_TituloTooLong() {
        String longTitle = "A".repeat(256); // Mais de 255 caracteres
        livro.setTitulo(longTitle);

        assertThrows(IllegalArgumentException.class, () -> {
            livro.validateBusinessRules();
        });
    }

    @Test
    void testValidateBusinessRules_InvalidYear() {
        livro.setAnoPublicacao(Year.of(1400)); // Antes da Prensa de Gutenberg

        assertThrows(IllegalArgumentException.class, () -> {
            livro.validateBusinessRules();
        });
    }

    @Test
    void testValidateBusinessRules_FutureYear() {
        livro.setAnoPublicacao(Year.now().plusYears(1)); // Ano futuro

        assertThrows(IllegalArgumentException.class, () -> {
            livro.validateBusinessRules();
        });
    }

    @Test
    void testValidateBusinessRules_InvalidPages() {
        livro.setPaginas(0);

        assertThrows(IllegalArgumentException.class, () -> {
            livro.validateBusinessRules();
        });
    }

    @Test
    void testValidateBusinessRules_TooManyPages() {
        livro.setPaginas(10001);

        assertThrows(IllegalArgumentException.class, () -> {
            livro.validateBusinessRules();
        });
    }

    @Test
    void testValidateBusinessRules_InvalidPrice() {
        livro.setPreco(-1.0);

        assertThrows(IllegalArgumentException.class, () -> {
            livro.validateBusinessRules();
        });
    }

    @Test
    void testValidateBusinessRules_PriceTooHigh() {
        livro.setPreco(1000000.0);

        assertThrows(IllegalArgumentException.class, () -> {
            livro.validateBusinessRules();
        });
    }

    @Test
    void testValidISBN10() {
        livro.setIsbn("8535902775"); // ISBN-10 válido
        assertTrue(livro.isValidForCreation());
    }

    @Test
    void testValidISBN13() {
        livro.setIsbn("978-85-359-0277-5"); // ISBN-13 válido com hífens
        assertTrue(livro.isValidForCreation());
    }

    @Test
    void testValidISBN13WithoutHyphens() {
        livro.setIsbn("9788535902775"); // ISBN-13 válido sem hífens
        assertTrue(livro.isValidForCreation());
    }

    @Test
    void testValidUpdateRules_PartialUpdate() {
        Livro updateLivro = new Livro();
        updateLivro.setTitulo("Novo Título");
        updateLivro.setPreco(39.90);

        assertTrue(updateLivro.isValidForUpdate());
    }

    @Test
    void testValidUpdateRules_InvalidPartialUpdate() {
        Livro updateLivro = new Livro();
        updateLivro.setTitulo(""); // Título vazio é inválido

        assertFalse(updateLivro.isValidForUpdate());
    }

    @Test
    void testEqualsAndHashCode() {
        Livro livro2 = new Livro(
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

        assertEquals(livro, livro2);
        assertEquals(livro.hashCode(), livro2.hashCode());
    }

    @Test
    void testToString() {
        String result = livro.toString();
        assertNotNull(result);
        assertTrue(result.contains("Dom Casmurro"));
        assertTrue(result.contains("Machado de Assis"));
    }
}
