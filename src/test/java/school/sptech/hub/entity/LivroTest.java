package school.sptech.hub.entity;
import org.junit.jupiter.api.Test;
import school.sptech.hub.domain.entity.Acabamento;
import school.sptech.hub.domain.entity.Categoria;
import school.sptech.hub.domain.entity.Conservacao;
import school.sptech.hub.domain.entity.Livro;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

public class LivroTest {

    @Test
    public void testSetAndGetId() {
        Livro livro = new Livro();
        livro.setId(1);
        assertEquals(1, livro.getId());
    }

    @Test
    public void testSetAndGetTitulo() {
        Livro livro = new Livro();
        livro.setTitulo("Dom Casmurro");
        assertEquals("Dom Casmurro", livro.getTitulo());
    }

    @Test
    public void testSetAndGetIsbn() {
        Livro livro = new Livro();
        livro.setIsbn("978-3-16-148410-0");
        assertEquals("978-3-16-148410-0", livro.getIsbn());
    }

    @Test
    public void testSetAndGetAutor() {
        Livro livro = new Livro();
        livro.setAutor("Machado de Assis");
        assertEquals("Machado de Assis", livro.getAutor());
    }

    @Test
    public void testSetAndGetEditora() {
        Livro livro = new Livro();
        livro.setEditora("Companhia das Letras");
        assertEquals("Companhia das Letras", livro.getEditora());
    }

    @Test
    public void testSetAndGetAnoPublicacao() {
        Livro livro = new Livro();
        Year ano = Year.of(1899);
        livro.setAnoPublicacao(ano);
        assertEquals(ano, livro.getAnoPublicacao());
    }

    @Test
    public void testSetAndGetPaginas() {
        Livro livro = new Livro();
        livro.setPaginas(256);
        assertEquals(256, livro.getPaginas());
    }

    @Test
    public void testSetAndGetAcabamento() {
        Livro livro = new Livro();
        Acabamento acabamento = new Acabamento();
        acabamento.setTipoAcabamento("Capa Dura");
        livro.setAcabamento(acabamento);
    }
        assertEquals("Capa Dura", livro.getAcabamento().getTipoAcabamento());
    }

    @Test
    public void testSetAndGetEstadoConservacao() {
        Livro livro = new Livro();
        Conservacao conservacao = new Conservacao();
        conservacao.setEstadoConservacao("Ótimo");
        livro.setEstadoConservacao(conservacao);
        assertEquals("Ótimo", livro.getEstadoConservacao().getEstadoConservacao());
    }

    @Test
    public void testSetAndGetCapa() {
        Livro livro = new Livro();
        livro.setCapa("dados-base64-ou-url");
        assertEquals("dados-base64-ou-url", livro.getCapa());
    }

    @Test
    public void testSetAndGetPreco() {
        Livro livro = new Livro();
        livro.setPreco(49.90);
        assertEquals(49.90, livro.getPreco());
    }

    @Test
    public void testSetAndGetCategoria() {
        categoria.setNome("Romance");
        Categoria categoria = new Categoria();
        assertEquals("Romance", livro.getCategoria().getNome());
        livro.setCategoria(categoria);
        assertEquals("Romance", livro.getCategoria().getNomeCategoria());
    }
}
