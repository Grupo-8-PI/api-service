package school.sptech.hub.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import school.sptech.hub.domain.entity.TipoConservacao;

import static org.junit.jupiter.api.Assertions.*;

public class TipoConservacaoTest {

    @Test
    @DisplayName("Deve retornar valores corretos do enum")
    public void testEnumValues() {
        // Act & Assert
        assertEquals(4, TipoConservacao.values().length, "Deve haver 4 tipos de conservação");

        // Verificar EXCELENTE
        assertEquals(1, TipoConservacao.EXCELENTE.getId());
        assertEquals("EXCELENTE", TipoConservacao.EXCELENTE.getDescricao());

        // Verificar BOM
        assertEquals(2, TipoConservacao.BOM.getId());
        assertEquals("BOM", TipoConservacao.BOM.getDescricao());

        // Verificar RAZOAVEL
        assertEquals(3, TipoConservacao.RAZOAVEL.getId());
        assertEquals("RAZOÁVEL", TipoConservacao.RAZOAVEL.getDescricao());

        // Verificar DEGRADADO
        assertEquals(4, TipoConservacao.DEGRADADO.getId());
        assertEquals("DEGRADADO", TipoConservacao.DEGRADADO.getDescricao());
    }

    @Test
    @DisplayName("Deve encontrar TipoConservacao por ID válido")
    public void testFromIdValido() {
        // Act & Assert
        assertEquals(TipoConservacao.EXCELENTE, TipoConservacao.fromId(1));
        assertEquals(TipoConservacao.BOM, TipoConservacao.fromId(2));
        assertEquals(TipoConservacao.RAZOAVEL, TipoConservacao.fromId(3));
        assertEquals(TipoConservacao.DEGRADADO, TipoConservacao.fromId(4));
    }

    @Test
    @DisplayName("Deve lançar exceção para ID nulo")
    public void testFromIdNulo() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> TipoConservacao.fromId(null)
        );
        assertEquals("ID da conservação não pode ser nulo", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção para ID inválido")
    public void testFromIdInvalido() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> TipoConservacao.fromId(999)
        );
        assertTrue(exception.getMessage().contains("ID de conservação inválido: 999"));
        assertTrue(exception.getMessage().contains("IDs válidos: 1-EXCELENTE, 2-BOM, 3-RAZOÁVEL, 4-DEGRADADO"));
    }

    @Test
    @DisplayName("Deve encontrar TipoConservacao por descrição válida")
    public void testFromDescricaoValida() {
        // Act & Assert
        assertEquals(TipoConservacao.EXCELENTE, TipoConservacao.fromDescricao("EXCELENTE"));
        assertEquals(TipoConservacao.BOM, TipoConservacao.fromDescricao("BOM"));
        assertEquals(TipoConservacao.RAZOAVEL, TipoConservacao.fromDescricao("RAZOÁVEL"));
        assertEquals(TipoConservacao.DEGRADADO, TipoConservacao.fromDescricao("DEGRADADO"));

        // Testar case insensitive
        assertEquals(TipoConservacao.EXCELENTE, TipoConservacao.fromDescricao("excelente"));
        assertEquals(TipoConservacao.BOM, TipoConservacao.fromDescricao("bom"));
        assertEquals(TipoConservacao.RAZOAVEL, TipoConservacao.fromDescricao("razoável"));
        assertEquals(TipoConservacao.DEGRADADO, TipoConservacao.fromDescricao("degradado"));

        // Testar com espaços
        assertEquals(TipoConservacao.EXCELENTE, TipoConservacao.fromDescricao("  EXCELENTE  "));
        assertEquals(TipoConservacao.BOM, TipoConservacao.fromDescricao("  BOM  "));
        assertEquals(TipoConservacao.RAZOAVEL, TipoConservacao.fromDescricao("  RAZOÁVEL  "));
        assertEquals(TipoConservacao.DEGRADADO, TipoConservacao.fromDescricao("  DEGRADADO  "));
    }

    @Test
    @DisplayName("Deve lançar exceção para descrição nula")
    public void testFromDescricaoNula() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> TipoConservacao.fromDescricao(null)
        );
        assertEquals("Descrição da conservação não pode ser nula ou vazia", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção para descrição vazia")
    public void testFromDescricaoVazia() {
        // Act & Assert
        IllegalArgumentException exception1 = assertThrows(
            IllegalArgumentException.class,
            () -> TipoConservacao.fromDescricao("")
        );
        assertEquals("Descrição da conservação não pode ser nula ou vazia", exception1.getMessage());

        IllegalArgumentException exception2 = assertThrows(
            IllegalArgumentException.class,
            () -> TipoConservacao.fromDescricao("   ")
        );
        assertEquals("Descrição da conservação não pode ser nula ou vazia", exception2.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção para descrição inválida")
    public void testFromDescricaoInvalida() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> TipoConservacao.fromDescricao("INVÁLIDO")
        );
        assertTrue(exception.getMessage().contains("Descrição de conservação inválida: INVÁLIDO"));
        assertTrue(exception.getMessage().contains("Valores válidos: EXCELENTE, BOM, RAZOÁVEL, DEGRADADO"));
    }

    @Test
    @DisplayName("Deve verificar igualdade e toString")
    public void testEqualityAndToString() {
        // Arrange
        TipoConservacao tipo1 = TipoConservacao.EXCELENTE;
        TipoConservacao tipo2 = TipoConservacao.EXCELENTE;
        TipoConservacao tipo3 = TipoConservacao.BOM;

        // Act & Assert
        assertEquals(tipo1, tipo2, "Mesma instância do enum deve ser igual");
        assertNotEquals(tipo1, tipo3, "Instâncias diferentes do enum devem ser diferentes");

        // Verificar toString
        assertNotNull(tipo1.toString());
        assertNotNull(tipo3.toString());
    }

    @Test
    @DisplayName("Deve testar ordinalidade dos enums")
    public void testEnumOrdinal() {
        // Act & Assert
        assertTrue(TipoConservacao.EXCELENTE.ordinal() < TipoConservacao.BOM.ordinal());
        assertTrue(TipoConservacao.BOM.ordinal() < TipoConservacao.RAZOAVEL.ordinal());
        assertTrue(TipoConservacao.RAZOAVEL.ordinal() < TipoConservacao.DEGRADADO.ordinal());
    }

    @Test
    @DisplayName("Deve verificar hierarquia de conservação por ID")
    public void testHierarchyById() {
        // Act & Assert
        assertTrue(TipoConservacao.EXCELENTE.getId() < TipoConservacao.BOM.getId());
        assertTrue(TipoConservacao.BOM.getId() < TipoConservacao.RAZOAVEL.getId());
        assertTrue(TipoConservacao.RAZOAVEL.getId() < TipoConservacao.DEGRADADO.getId());
    }
}
