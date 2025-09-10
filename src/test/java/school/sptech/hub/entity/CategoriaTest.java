package school.sptech.hub.entity;

import org.junit.jupiter.api.Test;
import school.sptech.hub.domain.entity.Categoria;

import static org.junit.jupiter.api.Assertions.*;

public class CategoriaTest {

    @Test
    public void testSetAndGetId() {
        Categoria categoria = new Categoria();
        categoria.setId(5);

        assertEquals(5, categoria.getId(), "O ID deve ser 5");
    }

    @Test
    public void testSetAndGetNomeCategoria() {
        Categoria categoria = new Categoria();
        categoria.setNomeCategoria("Tecnologia");

        assertEquals("Tecnologia", categoria.getNomeCategoria(), "O nome da categoria deve ser 'Tecnologia'");
    }

    @Test
    public void testNomeCategoriaNull() {
        Categoria categoria = new Categoria();
        categoria.setNomeCategoria(null);

        assertNull(categoria.getNomeCategoria(), "O nome da categoria deve ser null");
    }

    @Test
    public void testNomeCategoriaEmptyString() {
        Categoria categoria = new Categoria();
        categoria.setNomeCategoria("");

        assertEquals("", categoria.getNomeCategoria(), "O nome da categoria deve ser uma string vazia");
    }

    @Test
    public void testDefaultValues() {
        Categoria categoria = new Categoria();

        assertNull(categoria.getId(), "ID padrão deve ser null");
        assertNull(categoria.getNomeCategoria(), "Nome da categoria padrão deve ser null");
    }
}
