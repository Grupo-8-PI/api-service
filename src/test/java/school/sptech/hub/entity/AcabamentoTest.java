package school.sptech.hub.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.hub.domain.entity.Acabamento;
import school.sptech.hub.domain.entity.TipoAcabamento;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AcabamentoTest {

    private Acabamento acabamento;

    @BeforeEach
    void setUp() {
        acabamento = new Acabamento();
    }

    @Test
    @DisplayName("Deve definir e obter ID corretamente")
    public void testSetAndGetId() {
        // Arrange & Act
        acabamento.setId(1);

        // Assert
        assertEquals(1, acabamento.getId(), "O ID deve ser 1");
    }

    @Test
    @DisplayName("Deve definir e obter tipo de acabamento usando enum")
    public void testSetAndGetTipoAcabamento() {
        // Arrange & Act
        acabamento.setTipoAcabamento(TipoAcabamento.CAPA_DURA);

        // Assert
        assertEquals(TipoAcabamento.CAPA_DURA, acabamento.getTipoAcabamento(),
                "O tipo de acabamento deve ser CAPA_DURA");
    }

    @Test
    @DisplayName("Deve permitir tipo de acabamento null")
    public void testTipoAcabamentoNull() {
        // Arrange & Act
        acabamento.setTipoAcabamento(null);

        // Assert
        assertNull(acabamento.getTipoAcabamento(), "O tipo de acabamento deve ser null");
    }

    @Test
    @DisplayName("Deve criar acabamento com construtor que aceita ID e enum")
    public void testConstructorWithIdAndEnum() {
        // Arrange & Act
        Acabamento acabamentoComEnum = new Acabamento(1, TipoAcabamento.CAPA_DURA);

        // Assert
        assertEquals(1, acabamentoComEnum.getId());
        assertEquals(TipoAcabamento.CAPA_DURA, acabamentoComEnum.getTipoAcabamento());
    }

    @Test
    @DisplayName("Deve criar acabamento com construtor que aceita apenas ID")
    public void testConstructorWithIdOnly() {
        // Arrange & Act
        Acabamento acabamentoComId = new Acabamento(1);

        // Assert
        assertEquals(1, acabamentoComId.getId());
        assertEquals(TipoAcabamento.CAPA_DURA, acabamentoComId.getTipoAcabamento(),
                "Deve inferir TipoAcabamento.CAPA_DURA para ID 1");
    }

    @Test
    @DisplayName("Deve criar acabamento BROCHURA com ID 2")
    public void testConstructorWithIdTwo() {
        // Arrange & Act
        Acabamento acabamentoBrochura = new Acabamento(2);

        // Assert
        assertEquals(2, acabamentoBrochura.getId());
        assertEquals(TipoAcabamento.BROCHURA, acabamentoBrochura.getTipoAcabamento(),
                "Deve inferir TipoAcabamento.BROCHURA para ID 2");
    }

    @Test
    @DisplayName("Deve ter valores padrão nulos")
    public void testDefaultValues() {
        // Arrange & Act
        Acabamento acabamentoPadrao = new Acabamento();

        // Assert
        assertNull(acabamentoPadrao.getId(), "ID padrão deve ser null");
        assertNull(acabamentoPadrao.getTipoAcabamento(), "Tipo de acabamento padrão deve ser null");
    }

    @Test
    @DisplayName("Deve validar acabamento para criação com dados válidos")
    public void testValidationForCreationWithValidData() {
        // Arrange
        acabamento.setId(1);
        acabamento.setTipoAcabamento(TipoAcabamento.CAPA_DURA);

        // Act & Assert
        assertTrue(acabamento.isValidForCreation(),
                "Acabamento com dados válidos deve passar na validação para criação");
    }

    @Test
    @DisplayName("Deve falhar na validação para criação com ID inválido")
    public void testValidationForCreationWithInvalidId() {
        // Arrange & Act & Assert
        // Como o setId() já valida automaticamente, testamos que uma exceção é lançada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> acabamento.setId(3),
                "Deve lançar exceção ao tentar definir ID inválido");

        assertTrue(exception.getMessage().contains("ID de acabamento inválido"),
                "Mensagem da exceção deve mencionar ID inválido");
    }

    @Test
    @DisplayName("Deve falhar na validação para criação com tipo null")
    public void testValidationForCreationWithNullType() {
        // Arrange
        acabamento.setId(1);
        acabamento.setTipoAcabamento(null);

        // Act & Assert
        assertFalse(acabamento.isValidForCreation(),
                "Acabamento com tipo null deve falhar na validação");
    }

    @Test
    @DisplayName("Deve validar regras de negócio e lançar exceção para dados inválidos")
    public void testBusinessRulesValidationThrowsException() {
        // Arrange
        acabamento.setId(1);
        acabamento.setTipoAcabamento(null); // Tipo null para forçar erro de validação

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> acabamento.validateBusinessRules(),
                "Deve lançar exceção para dados que não atendem às regras de negócio");

        assertTrue(exception.getMessage().contains("regras de negócio"),
                "Mensagem da exceção deve mencionar regras de negócio");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar definir ID inválido diretamente")
    public void testSetInvalidIdThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> acabamento.setId(5),
                "Deve lançar exceção ao tentar definir ID inválido");

        assertTrue(exception.getMessage().contains("ID de acabamento inválido: 5"),
                "Mensagem deve conter o ID inválido específico");
    }

    @Test
    @DisplayName("Deve sincronizar ID e TipoAcabamento automaticamente")
    public void testIdAndTypeSync() {
        // Test setId sincroniza o tipo
        acabamento.setId(1);
        assertEquals(TipoAcabamento.CAPA_DURA, acabamento.getTipoAcabamento(),
                "Definir ID 1 deve definir tipo CAPA_DURA automaticamente");

        // Test setTipoAcabamento sincroniza o ID
        acabamento.setTipoAcabamento(TipoAcabamento.BROCHURA);
        assertEquals(2, acabamento.getId(),
                "Definir tipo BROCHURA deve definir ID 2 automaticamente");
    }

    @Test
    @DisplayName("Deve testar todos os valores do enum TipoAcabamento")
    public void testAllEnumValues() {
        // Test CAPA_DURA
        assertEquals(1, TipoAcabamento.CAPA_DURA.getId());
        assertEquals("CAPA DURA", TipoAcabamento.CAPA_DURA.getDescricao());

        // Test BROCHURA
        assertEquals(2, TipoAcabamento.BROCHURA.getId());
        assertEquals("BROCHURA", TipoAcabamento.BROCHURA.getDescricao());
    }

    @Test
    @DisplayName("Deve verificar igualdade entre acabamentos")
    public void testEquality() {
        // Arrange
        Acabamento acabamento1 = new Acabamento(1, TipoAcabamento.CAPA_DURA);
        Acabamento acabamento2 = new Acabamento(1, TipoAcabamento.CAPA_DURA);
        Acabamento acabamento3 = new Acabamento(2, TipoAcabamento.BROCHURA);

        // Act & Assert
        assertEquals(acabamento1, acabamento2, "Acabamentos com mesmos dados devem ser iguais");
        assertNotEquals(acabamento1, acabamento3, "Acabamentos com dados diferentes devem ser diferentes");
        assertEquals(acabamento1.hashCode(), acabamento2.hashCode(), "HashCodes devem ser iguais para objetos iguais");
    }
}
