package school.sptech.hub.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.hub.domain.entity.Conservacao;
import school.sptech.hub.domain.entity.TipoConservacao;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ConservacaoTest {

    private Conservacao conservacao;

    @BeforeEach
    void setUp() {
        conservacao = new Conservacao();
    }

    @Test
    @DisplayName("Deve definir e obter ID corretamente")
    public void testSetAndGetId() {
        // Arrange & Act
        conservacao.setId(2); // ID válido (1-4)

        // Assert
        assertEquals(2, conservacao.getId(), "O ID deve ser 2");
        assertEquals(TipoConservacao.BOM, conservacao.getTipoConservacao(), "Tipo deve ser BOM para ID 2");
    }

    @Test
    @DisplayName("Deve lançar exceção ao definir ID inválido")
    public void testSetInvalidId() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> conservacao.setId(7), // ID inválido
                "Deve lançar exceção ao definir ID inválido");

        assertTrue(exception.getMessage().contains("ID de conservação inválido: 7"),
                "Mensagem deve conter o ID inválido específico");
    }

    @Test
    @DisplayName("Deve definir e obter estado de conservação corretamente")
    public void testSetAndGetEstadoConservacao() {
        // Arrange & Act
        conservacao.setEstadoConservacao("BOM"); // Usar descrição em maiúscula

        // Assert
        assertEquals("BOM", conservacao.getEstadoConservacao(), "O estado de conservação deve ser 'BOM'");
        assertEquals(TipoConservacao.BOM, conservacao.getTipoConservacao(), "Tipo deve ser BOM");
        assertEquals(2, conservacao.getId(), "ID deve ser sincronizado para 2");
    }

    @Test
    @DisplayName("Deve permitir estado de conservação null")
    public void testEstadoConservacaoNull() {
        // Arrange & Act
        conservacao.setEstadoConservacao(null);

        // Assert
        assertNull(conservacao.getEstadoConservacao(), "O estado de conservação deve ser null");
        assertNull(conservacao.getTipoConservacao(), "Tipo de conservação deve ser null");
    }

    @Test
    @DisplayName("Deve lançar exceção para string vazia")
    public void testEstadoConservacaoEmptyString() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> conservacao.setEstadoConservacao(""),
                "Deve lançar exceção para string vazia");

        assertTrue(exception.getMessage().contains("Descrição da conservação não pode ser nula ou vazia"),
                "Mensagem deve indicar que descrição não pode ser vazia");
    }

    @Test
    @DisplayName("Deve ter valores padrão nulos")
    public void testDefaultValues() {
        // Arrange & Act
        Conservacao conservacaoPadrao = new Conservacao();

        // Assert
        assertNull(conservacaoPadrao.getId(), "ID padrão deve ser null");
        assertNull(conservacaoPadrao.getEstadoConservacao(), "Estado de conservação padrão deve ser null");
        assertNull(conservacaoPadrao.getTipoConservacao(), "Tipo de conservação padrão deve ser null");
    }

    @Test
    @DisplayName("Deve criar conservação com construtor completo")
    public void testConstructorWithIdAndEnum() {
        // Arrange & Act
        Conservacao conservacaoCompleta = new Conservacao(3, TipoConservacao.RAZOAVEL);

        // Assert
        assertEquals(3, conservacaoCompleta.getId());
        assertEquals(TipoConservacao.RAZOAVEL, conservacaoCompleta.getTipoConservacao());
        assertEquals("RAZOÁVEL", conservacaoCompleta.getEstadoConservacao());
    }

    @Test
    @DisplayName("Deve criar conservação com construtor que aceita apenas ID")
    public void testConstructorWithIdOnly() {
        // Arrange & Act
        Conservacao conservacaoComId = new Conservacao(1);

        // Assert
        assertEquals(1, conservacaoComId.getId());
        assertEquals(TipoConservacao.EXCELENTE, conservacaoComId.getTipoConservacao());
        assertEquals("EXCELENTE", conservacaoComId.getEstadoConservacao());
    }

    @Test
    @DisplayName("Deve validar conservação para criação com dados válidos")
    public void testValidationForCreationWithValidData() {
        // Arrange
        conservacao.setId(4);

        // Act & Assert
        assertTrue(conservacao.isValidForCreation(),
                "Conservação com dados válidos deve passar na validação para criação");
    }

    @Test
    @DisplayName("Deve falhar na validação para criação com tipo null")
    public void testValidationForCreationWithNullType() {
        // Arrange
        conservacao.setId(1);
        conservacao.setTipoConservacao(null);

        // Act & Assert
        assertFalse(conservacao.isValidForCreation(),
                "Conservação com tipo null deve falhar na validação");
    }

    @Test
    @DisplayName("Deve testar todos os valores do enum TipoConservacao")
    public void testAllEnumValues() {
        // Test EXCELENTE
        assertEquals(1, TipoConservacao.EXCELENTE.getId());
        assertEquals("EXCELENTE", TipoConservacao.EXCELENTE.getDescricao());

        // Test BOM
        assertEquals(2, TipoConservacao.BOM.getId());
        assertEquals("BOM", TipoConservacao.BOM.getDescricao());

        // Test RAZOAVEL
        assertEquals(3, TipoConservacao.RAZOAVEL.getId());
        assertEquals("RAZOÁVEL", TipoConservacao.RAZOAVEL.getDescricao());

        // Test DEGRADADO
        assertEquals(4, TipoConservacao.DEGRADADO.getId());
        assertEquals("DEGRADADO", TipoConservacao.DEGRADADO.getDescricao());
    }

    @Test
    @DisplayName("Deve sincronizar ID e TipoConservacao automaticamente")
    public void testIdAndTypeSync() {
        // Test setId sincroniza o tipo
        conservacao.setId(3);
        assertEquals(TipoConservacao.RAZOAVEL, conservacao.getTipoConservacao(),
                "Definir ID 3 deve definir tipo RAZOAVEL automaticamente");

        // Test setTipoConservacao sincroniza o ID
        conservacao.setTipoConservacao(TipoConservacao.DEGRADADO);
        assertEquals(4, conservacao.getId(),
                "Definir tipo DEGRADADO deve definir ID 4 automaticamente");
    }

    @Test
    @DisplayName("Deve verificar igualdade entre conservações")
    public void testEquality() {
        // Arrange
        Conservacao conservacao1 = new Conservacao(2, TipoConservacao.BOM);
        Conservacao conservacao2 = new Conservacao(2, TipoConservacao.BOM);
        Conservacao conservacao3 = new Conservacao(1, TipoConservacao.EXCELENTE);

        // Act & Assert
        assertEquals(conservacao1, conservacao2, "Conservações com mesmos dados devem ser iguais");
        assertNotEquals(conservacao1, conservacao3, "Conservações com dados diferentes devem ser diferentes");
        assertEquals(conservacao1.hashCode(), conservacao2.hashCode(), "HashCodes devem ser iguais para objetos iguais");
    }

    @Test
    @DisplayName("Deve validar regras de negócio e lançar exceção para dados inválidos")
    public void testBusinessRulesValidationThrowsException() {
        // Arrange
        conservacao.setId(2);
        conservacao.setTipoConservacao(null); // Tipo null para forçar erro

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> conservacao.validateBusinessRules(),
                "Deve lançar exceção para dados que não atendem às regras de negócio");

        assertTrue(exception.getMessage().contains("regras de negócio"),
                "Mensagem da exceção deve mencionar regras de negócio");
    }

    @Test
    @DisplayName("Deve aceitar descrições case-insensitive")
    public void testCaseInsensitiveDescricao() {
        // Test diferentes casos
        conservacao.setEstadoConservacao("bom"); // minúscula
        assertEquals("BOM", conservacao.getEstadoConservacao());
        assertEquals(TipoConservacao.BOM, conservacao.getTipoConservacao());

        conservacao.setEstadoConservacao("  EXCELENTE  "); // com espaços
        assertEquals("EXCELENTE", conservacao.getEstadoConservacao());
        assertEquals(TipoConservacao.EXCELENTE, conservacao.getTipoConservacao());
    }
}
