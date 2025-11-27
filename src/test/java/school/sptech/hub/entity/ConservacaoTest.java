package school.sptech.hub.entity;

import org.junit.jupiter.api.Test;
import school.sptech.hub.domain.entity.Conservacao;

import static org.junit.jupiter.api.Assertions.*;

public class ConservacaoTest {

    @Test
    public void testSetAndGetId() {
        Conservacao conservacao = new Conservacao();
        conservacao.setId(7);

        assertEquals(7, conservacao.getId(), "O ID deve ser 7");
    }

    @Test
    public void testSetAndGetEstadoConservacao() {
        Conservacao conservacao = new Conservacao();
        conservacao.setEstadoConservacao("Bom");

        assertEquals("Bom", conservacao.getEstadoConservacao(), "O estado de conservação deve ser 'Bom'");
    }

    @Test
    public void testEstadoConservacaoNull() {
        Conservacao conservacao = new Conservacao();
        conservacao.setEstadoConservacao(null);

        assertNull(conservacao.getEstadoConservacao(), "O estado de conservação deve ser null");
    }

    @Test
    public void testEstadoConservacaoEmptyString() {
        Conservacao conservacao = new Conservacao();
        conservacao.setEstadoConservacao("");

        assertEquals("", conservacao.getEstadoConservacao(), "O estado de conservação deve ser uma string vazia");
    }

    @Test
    public void testDefaultValues() {
        Conservacao conservacao = new Conservacao();

        assertNull(conservacao.getId(), "ID padrão deve ser null");
        assertNull(conservacao.getEstadoConservacao(), "Estado de conservação padrão deve ser null");
    }
}
