package entity_test;

import org.junit.jupiter.api.Test;
import school.sptech.hub.entity.Acabamento;

import static org.junit.jupiter.api.Assertions.*;

public class AcabamentoTest {

    @Test
    public void testSetAndGetId() {
        Acabamento acabamento = new Acabamento();
        acabamento.setId(10);

        assertEquals(10, acabamento.getId(), "O ID deve ser 10");
    }

    @Test
    public void testSetAndGetTipoAcabamento() {
        Acabamento acabamento = new Acabamento();
        acabamento.setTipoAcabamento("Polido");

        assertEquals("Polido", acabamento.getTipoAcabamento(), "O tipo de acabamento deve ser 'Polido'");
    }

    @Test
    public void testTipoAcabamentoNull() {
        Acabamento acabamento = new Acabamento();
        acabamento.setTipoAcabamento(null);

        assertNull(acabamento.getTipoAcabamento(), "O tipo de acabamento deve ser null");
    }

    @Test
    public void testTipoAcabamentoMaxLength() {
        Acabamento acabamento = new Acabamento();
        String tipo = "A".repeat(45);
        acabamento.setTipoAcabamento(tipo);

        assertEquals(45, acabamento.getTipoAcabamento().length(), "O tipo de acabamento deve ter 45 caracteres");
    }

    @Test
    public void testDefaultValues() {
        Acabamento acabamento = new Acabamento();

        assertNull(acabamento.getId(), "ID padrão deve ser null");
        assertNull(acabamento.getTipoAcabamento(), "Tipo de acabamento padrão deve ser null");
    }
}
