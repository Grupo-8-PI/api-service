package school.sptech.hub.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import school.sptech.hub.domain.entity.TipoAcabamento;

import static org.junit.jupiter.api.Assertions.*;

public class TipoAcabamentoTest {

    @Test
    @DisplayName("Deve retornar valores corretos do enum")
    public void testEnumValues() {
        // Act & Assert
        assertEquals(2, TipoAcabamento.values().length, "Deve haver 2 tipos de acabamento");

        // Verificar CAPA_DURA
        assertEquals(1, TipoAcabamento.CAPA_DURA.getId());
        assertEquals("CAPA DURA", TipoAcabamento.CAPA_DURA.getDescricao());

        // Verificar BROCHURA
        assertEquals(2, TipoAcabamento.BROCHURA.getId());
        assertEquals("BROCHURA", TipoAcabamento.BROCHURA.getDescricao());
    }

    @Test
    @DisplayName("Deve encontrar TipoAcabamento por ID válido")
    public void testFromIdValido() {
        // Act & Assert
        assertEquals(TipoAcabamento.CAPA_DURA, TipoAcabamento.fromId(1));
        assertEquals(TipoAcabamento.BROCHURA, TipoAcabamento.fromId(2));
    }

    @Test
    @DisplayName("Deve lançar exceção para ID nulo")
    public void testFromIdNulo() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> TipoAcabamento.fromId(null)
        );
        assertEquals("ID do acabamento não pode ser nulo", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção para ID inválido")
    public void testFromIdInvalido() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> TipoAcabamento.fromId(999)
        );
        assertTrue(exception.getMessage().contains("ID de acabamento inválido: 999"));
        assertTrue(exception.getMessage().contains("IDs válidos: 1-CAPA DURA, 2-BROCHURA"));
    }

    @Test
    @DisplayName("Deve encontrar TipoAcabamento por descrição válida")
    public void testFromDescricaoValida() {
        // Act & Assert
        assertEquals(TipoAcabamento.CAPA_DURA, TipoAcabamento.fromDescricao("CAPA DURA"));
        assertEquals(TipoAcabamento.BROCHURA, TipoAcabamento.fromDescricao("BROCHURA"));

        // Testar case insensitive
        assertEquals(TipoAcabamento.CAPA_DURA, TipoAcabamento.fromDescricao("capa dura"));
        assertEquals(TipoAcabamento.BROCHURA, TipoAcabamento.fromDescricao("brochura"));

        // Testar com espaços
        assertEquals(TipoAcabamento.CAPA_DURA, TipoAcabamento.fromDescricao("  CAPA DURA  "));
        assertEquals(TipoAcabamento.BROCHURA, TipoAcabamento.fromDescricao("  BROCHURA  "));
    }

    @Test
    @DisplayName("Deve lançar exceção para descrição nula")
    public void testFromDescricaoNula() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> TipoAcabamento.fromDescricao(null)
        );
        assertEquals("Descrição do acabamento não pode ser nula ou vazia", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção para descrição vazia")
    public void testFromDescricaoVazia() {
        // Act & Assert
        IllegalArgumentException exception1 = assertThrows(
            IllegalArgumentException.class,
            () -> TipoAcabamento.fromDescricao("")
        );
        assertEquals("Descrição do acabamento não pode ser nula ou vazia", exception1.getMessage());

        IllegalArgumentException exception2 = assertThrows(
            IllegalArgumentException.class,
            () -> TipoAcabamento.fromDescricao("   ")
        );
        assertEquals("Descrição do acabamento não pode ser nula ou vazia", exception2.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção para descrição inválida")
    public void testFromDescricaoInvalida() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> TipoAcabamento.fromDescricao("INVÁLIDO")
        );
        assertTrue(exception.getMessage().contains("Descrição de acabamento inválida: INVÁLIDO"));
        assertTrue(exception.getMessage().contains("Valores válidos: CAPA DURA, BROCHURA"));
    }

    @Test
    @DisplayName("Deve verificar igualdade e toString")
    public void testEqualityAndToString() {
        // Arrange
        TipoAcabamento tipo1 = TipoAcabamento.CAPA_DURA;
        TipoAcabamento tipo2 = TipoAcabamento.CAPA_DURA;
        TipoAcabamento tipo3 = TipoAcabamento.BROCHURA;

        // Act & Assert
        assertEquals(tipo1, tipo2, "Mesma instância do enum deve ser igual");
        assertNotEquals(tipo1, tipo3, "Instâncias diferentes do enum devem ser diferentes");

        // Verificar toString
        assertNotNull(tipo1.toString());
        assertNotNull(tipo3.toString());
    }
}
