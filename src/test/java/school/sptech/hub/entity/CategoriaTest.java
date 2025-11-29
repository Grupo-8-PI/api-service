package school.sptech.hub.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.hub.domain.entity.Categoria;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CategoriaTest {

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
    }

    @Test
    @DisplayName("Deve definir e obter ID corretamente")
    public void testSetAndGetId() {
        // Arrange & Act
        categoria.setId(5);

        // Assert
        assertEquals(5, categoria.getId(), "O ID deve ser 5");
    }

    @Test
    @DisplayName("Deve definir e obter nome da categoria corretamente")
    public void testSetAndGetNome() {
        // Arrange & Act
        categoria.setNome("Tecnologia");

        // Assert
        assertEquals("Tecnologia", categoria.getNome(), "O nome da categoria deve ser 'Tecnologia'");
    }

    @Test
    @DisplayName("Deve permitir nome null")
    public void testNomeNull() {
        // Arrange & Act
        categoria.setNome(null);

        // Assert
        assertNull(categoria.getNome(), "O nome da categoria deve ser null");
    }

    @Test
    @DisplayName("Deve permitir nome como string vazia")
    public void testNomeEmptyString() {
        // Arrange & Act
        categoria.setNome("");

        // Assert
        assertEquals("", categoria.getNome(), "O nome da categoria deve ser uma string vazia");
    }

    @Test
    @DisplayName("Deve ter valores padrão nulos")
    public void testDefaultValues() {
        // Arrange & Act
        Categoria categoriaPadrao = new Categoria();

        // Assert
        assertNull(categoriaPadrao.getId(), "ID padrão deve ser null");
        assertNull(categoriaPadrao.getNome(), "Nome da categoria padrão deve ser null");
    }

    @Test
    @DisplayName("Deve criar categoria com construtor completo")
    public void testConstructorWithIdAndNome() {
        // Arrange & Act
        Categoria categoriaCompleta = new Categoria(1, "Ficção");

        // Assert
        assertEquals(1, categoriaCompleta.getId());
        assertEquals("Ficção", categoriaCompleta.getNome());
    }

    @Test
    @DisplayName("Deve validar categoria para criação com dados válidos")
    public void testValidationForCreationWithValidData() {
        // Arrange
        categoria.setId(1);
        categoria.setNome("Romance");

        // Act & Assert
        assertTrue(categoria.isValidForCreation(),
                "Categoria com dados válidos deve passar na validação para criação");
    }

    @Test
    @DisplayName("Deve falhar na validação para criação com nome null")
    public void testValidationForCreationWithNullNome() {
        // Arrange
        categoria.setId(1);
        categoria.setNome(null);

        // Act & Assert
        assertFalse(categoria.isValidForCreation(),
                "Categoria com nome null deve falhar na validação");
    }

    @Test
    @DisplayName("Deve falhar na validação para criação com nome vazio")
    public void testValidationForCreationWithEmptyNome() {
        // Arrange
        categoria.setId(1);
        categoria.setNome("");

        // Act & Assert
        assertFalse(categoria.isValidForCreation(),
                "Categoria com nome vazio deve falhar na validação");
    }

    @Test
    @DisplayName("Deve falhar na validação para criação com nome muito longo")
    public void testValidationForCreationWithLongNome() {
        // Arrange
        categoria.setId(1);
        categoria.setNome("A".repeat(46)); // Mais de 45 caracteres

        // Act & Assert
        assertFalse(categoria.isValidForCreation(),
                "Categoria com nome muito longo deve falhar na validação");
    }

    @Test
    @DisplayName("Deve validar regras de negócio e lançar exceção para dados inválidos")
    public void testBusinessRulesValidationThrowsException() {
        // Arrange
        categoria.setId(1);
        categoria.setNome(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> categoria.validateBusinessRules(),
                "Deve lançar exceção para dados que não atendem às regras de negócio");

        assertTrue(exception.getMessage().contains("regras de negócio"),
                "Mensagem da exceção deve mencionar regras de negócio");
    }

    @Test
    @DisplayName("Deve passar na validação de regras de negócio com dados válidos")
    public void testBusinessRulesValidationWithValidData() {
        // Arrange
        categoria.setId(1);
        categoria.setNome("Terror");

        // Act & Assert
        assertDoesNotThrow(() -> categoria.validateBusinessRules(),
                "Não deve lançar exceção para dados válidos");
    }

    @Test
    @DisplayName("Deve validar corretamente para atualização")
    public void testValidationForUpdate() {
        // Arrange
        categoria.setId(null); // ID null é válido para update
        categoria.setNome("Aventura");

        // Act & Assert
        assertTrue(categoria.isValidForUpdate(),
                "Categoria com nome válido deve ser válida para atualização");
    }

    @Test
    @DisplayName("Deve verificar igualdade entre categorias")
    public void testEquality() {
        // Arrange
        Categoria categoria1 = new Categoria(1, "Drama");
        Categoria categoria2 = new Categoria(1, "Drama");
        Categoria categoria3 = new Categoria(2, "Comédia");

        // Act & Assert
        assertEquals(categoria1, categoria2, "Categorias com mesmos dados devem ser iguais");
        assertNotEquals(categoria1, categoria3, "Categorias com dados diferentes devem ser diferentes");
        assertEquals(categoria1.hashCode(), categoria2.hashCode(), "HashCodes devem ser iguais para objetos iguais");
    }
}
