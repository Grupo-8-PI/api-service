package school.sptech.hub.entity;

import org.junit.jupiter.api.Test;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.entity.Acabamento;
import school.sptech.hub.domain.entity.Conservacao;
import school.sptech.hub.domain.entity.Categoria;

import java.time.LocalDateTime;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

public class LivroTest {

    @Test
    public void testGetAndSetId() {
        Livro livro = new Livro();
        livro.setId(1);
        assertEquals(1, livro.getId());
    }

    @Test
    public void testGetAndSetTitulo() {
        Livro livro = new Livro();
        livro.setTitulo("Clean Code");
        assertEquals("Clean Code", livro.getTitulo());
    }

    @Test
    public void testGetAndSetIsbn() {
        Livro livro = new Livro();
        livro.setIsbn("9780132350884");
        assertEquals("9780132350884", livro.getIsbn());
    }

    @Test
    public void testGetAndSetAutor() {
        Livro livro = new Livro();
        livro.setAutor("Robert C. Martin");
        assertEquals("Robert C. Martin", livro.getAutor());
    }

    @Test
    public void testGetAndSetEditora() {
        Livro livro = new Livro();
        livro.setEditora("Prentice Hall");
        assertEquals("Prentice Hall", livro.getEditora());
    }

    @Test
    public void testGetAndSetDescricao() {
        Livro livro = new Livro();
        livro.setDescricao("Um livro sobre código limpo");
        assertEquals("Um livro sobre código limpo", livro.getDescricao());
    }

    @Test
    public void testGetAndSetAnoPublicacao() {
        Livro livro = new Livro();
        Year ano = Year.of(2008);
        livro.setAnoPublicacao(ano);
        assertEquals(ano, livro.getAnoPublicacao());
    }

    @Test
    public void testGetAndSetPaginas() {
        Livro livro = new Livro();
        livro.setPaginas(464);
        assertEquals(464, livro.getPaginas());
    }

    @Test
    public void testGetAndSetPreco() {
        Livro livro = new Livro();
        livro.setPreco(89.90);
        assertEquals(89.90, livro.getPreco());
    }

    @Test
    public void testGetAndSetCapa() {
        Livro livro = new Livro();
        livro.setCapa("capa.jpg");
        assertEquals("capa.jpg", livro.getCapa());
    }

    @Test
    public void testGetAndSetAcabamento() {
        Livro livro = new Livro();
        Acabamento acabamento = new Acabamento(1);
        livro.setAcabamento(acabamento);
        assertEquals(acabamento, livro.getAcabamento());
    }

    @Test
    public void testGetAndSetEstadoConservacao() {
        Livro livro = new Livro();
        Conservacao conservacao = new Conservacao(1);
        livro.setEstadoConservacao(conservacao);
        assertEquals(conservacao, livro.getEstadoConservacao());
    }

    @Test
    public void testGetAndSetCategoria() {
        Livro livro = new Livro();
        Categoria categoria = new Categoria();
        categoria.setNome("Tecnologia");
        livro.setCategoria(categoria);
        assertEquals(categoria, livro.getCategoria());
    }

    @Test
    public void testGetAndSetDataAdicao() {
        Livro livro = new Livro();
        LocalDateTime data = LocalDateTime.now();
        livro.setDataAdicao(data);
        assertEquals(data, livro.getDataAdicao());
    }

    // ===== TESTES DE VALIDAÇÃO =====

    @Test
    public void testIsbnValidoFormat13Digitos() {
        Livro livro = createValidLivro();
        livro.setIsbn("9780132350884");
        assertTrue(livro.isValidForCreation(), "ISBN de 13 dígitos deve ser válido");
    }

    @Test
    public void testIsbnValidoFormat10Digitos() {
        Livro livro = createValidLivro();
        livro.setIsbn("013235088X");
        assertTrue(livro.isValidForCreation(), "ISBN de 10 dígitos deve ser válido");
    }

    @Test
    public void testIsbnInvalidoVazio() {
        Livro livro = createValidLivro();
        livro.setIsbn("");
        assertFalse(livro.isValidForCreation(), "ISBN vazio deve ser inválido");
    }

    @Test
    public void testIsbnInvalidoNull() {
        Livro livro = createValidLivro();
        livro.setIsbn(null);
        assertFalse(livro.isValidForCreation(), "ISBN nulo deve ser inválido");
    }

    @Test
    public void testTituloValidoNormal() {
        Livro livro = createValidLivro();
        livro.setTitulo("Clean Code");
        assertTrue(livro.isValidForCreation(), "Título válido deve passar na validação");
    }

    @Test
    public void testTituloInvalidoVazio() {
        Livro livro = createValidLivro();
        livro.setTitulo("");
        assertFalse(livro.isValidForCreation(), "Título vazio deve ser inválido");
    }

    @Test
    public void testTituloInvalidoNull() {
        Livro livro = createValidLivro();
        livro.setTitulo(null);
        assertFalse(livro.isValidForCreation(), "Título nulo deve ser inválido");
    }

    @Test
    public void testAutorValidoNormal() {
        Livro livro = createValidLivro();
        livro.setAutor("Robert C. Martin");
        assertTrue(livro.isValidForCreation(), "Autor válido deve passar na validação");
    }

    @Test
    public void testAutorInvalidoVazio() {
        Livro livro = createValidLivro();
        livro.setAutor("");
        assertFalse(livro.isValidForCreation(), "Autor vazio deve ser inválido");
    }

    @Test
    public void testAutorInvalidoNull() {
        Livro livro = createValidLivro();
        livro.setAutor(null);
        assertFalse(livro.isValidForCreation(), "Autor nulo deve ser inválido");
    }

    @Test
    public void testEditoraValidaNormal() {
        Livro livro = createValidLivro();
        livro.setEditora("Prentice Hall");
        assertTrue(livro.isValidForCreation(), "Editora válida deve passar na validação");
    }

    @Test
    public void testEditoraInvalidaVazia() {
        Livro livro = createValidLivro();
        livro.setEditora("");
        assertFalse(livro.isValidForCreation(), "Editora vazia deve ser inválida");
    }

    @Test
    public void testEditoraInvalidaNull() {
        Livro livro = createValidLivro();
        livro.setEditora(null);
        assertFalse(livro.isValidForCreation(), "Editora nula deve ser inválida");
    }

    @Test
    public void testAnoPublicacaoValidoAtual() {
        Livro livro = createValidLivro();
        livro.setAnoPublicacao(Year.now());
        assertTrue(livro.isValidForCreation(), "Ano atual deve ser válido");
    }

    @Test
    public void testAnoPublicacaoValidoAntigo() {
        Livro livro = createValidLivro();
        livro.setAnoPublicacao(Year.of(1450));
        assertTrue(livro.isValidForCreation(), "Ano de 1450 deve ser válido (limite mínimo)");
    }

    @Test
    public void testAnoPublicacaoInvalidoFuturo() {
        Livro livro = createValidLivro();
        livro.setAnoPublicacao(Year.now().plusYears(1));
        assertFalse(livro.isValidForCreation(), "Ano futuro deve ser inválido");
    }

    @Test
    public void testAnoPublicacaoInvalidoMuitoAntigo() {
        Livro livro = createValidLivro();
        livro.setAnoPublicacao(Year.of(1449));
        assertFalse(livro.isValidForCreation(), "Ano anterior a 1450 deve ser inválido");
    }

    @Test
    public void testAnoPublicacaoInvalidoNull() {
        Livro livro = createValidLivro();
        livro.setAnoPublicacao(null);
        assertFalse(livro.isValidForCreation(), "Ano de publicação nulo deve ser inválido");
    }

    @Test
    public void testPaginasValidasNormal() {
        Livro livro = createValidLivro();
        livro.setPaginas(300);
        assertTrue(livro.isValidForCreation(), "Número normal de páginas deve ser válido");
    }

    @Test
    public void testPaginasValidasMinimo() {
        Livro livro = createValidLivro();
        livro.setPaginas(1);
        assertTrue(livro.isValidForCreation(), "Uma página deve ser válida");
    }

    @Test
    public void testPaginasValidasMaximo() {
        Livro livro = createValidLivro();
        livro.setPaginas(10000);
        assertTrue(livro.isValidForCreation(), "10000 páginas deve ser válido (limite máximo)");
    }

    @Test
    public void testPaginasInvalidasZero() {
        Livro livro = createValidLivro();
        livro.setPaginas(0);
        assertFalse(livro.isValidForCreation(), "Zero páginas deve ser inválido");
    }

    @Test
    public void testPaginasInvalidasNegativas() {
        Livro livro = createValidLivro();
        livro.setPaginas(-1);
        assertFalse(livro.isValidForCreation(), "Páginas negativas deve ser inválido");
    }

    @Test
    public void testPaginasInvalidasMuitoAltas() {
        Livro livro = createValidLivro();
        livro.setPaginas(10001);
        assertFalse(livro.isValidForCreation(), "Mais de 10000 páginas deve ser inválido");
    }

    @Test
    public void testPaginasInvalidasNull() {
        Livro livro = createValidLivro();
        livro.setPaginas(null);
        assertFalse(livro.isValidForCreation(), "Páginas nulo deve ser inválido");
    }

    @Test
    public void testPrecoValidoNormal() {
        Livro livro = createValidLivro();
        livro.setPreco(29.99);
        assertTrue(livro.isValidForCreation(), "Preço normal deve ser válido");
    }

    @Test
    public void testPrecoValidoMinimo() {
        Livro livro = createValidLivro();
        livro.setPreco(0.01);
        assertTrue(livro.isValidForCreation(), "Preço mínimo (0.01) deve ser válido");
    }

    @Test
    public void testPrecoValidoMaximo() {
        Livro livro = createValidLivro();
        livro.setPreco(999999.99);
        assertTrue(livro.isValidForCreation(), "Preço máximo deve ser válido");
    }

    @Test
    public void testPrecoInvalidoZero() {
        Livro livro = createValidLivro();
        livro.setPreco(0.0);
        assertFalse(livro.isValidForCreation(), "Preço zero deve ser inválido");
    }

    @Test
    public void testPrecoInvalidoNegativo() {
        Livro livro = createValidLivro();
        livro.setPreco(-1.0);
        assertFalse(livro.isValidForCreation(), "Preço negativo deve ser inválido");
    }

    @Test
    public void testPrecoInvalidoMuitoAlto() {
        Livro livro = createValidLivro();
        livro.setPreco(1000000.00);
        assertFalse(livro.isValidForCreation(), "Preço muito alto deve ser inválido");
    }

    @Test
    public void testPrecoInvalidoNull() {
        Livro livro = createValidLivro();
        livro.setPreco(null);
        assertFalse(livro.isValidForCreation(), "Preço nulo deve ser inválido");
    }

    @Test
    public void testDataAdicaoValidaAgora() {
        Livro livro = createValidLivro();
        livro.setDataAdicao(LocalDateTime.now());
        assertTrue(livro.isValidForCreation(), "Data atual deve ser válida");
    }

    @Test
    public void testDataAdicaoValidaPassado() {
        Livro livro = createValidLivro();
        livro.setDataAdicao(LocalDateTime.now().minusDays(1));
        assertTrue(livro.isValidForCreation(), "Data no passado deve ser válida");
    }

    @Test
    public void testDataAdicaoInvalidaFutura() {
        Livro livro = createValidLivro();
        livro.setDataAdicao(LocalDateTime.now().plusDays(1));
        assertFalse(livro.isValidForCreation(), "Data futura deve ser inválida");
    }

    @Test
    public void testDataAdicaoInvalidaNull() {
        Livro livro = createValidLivro();
        livro.setDataAdicao(null);
        assertFalse(livro.isValidForCreation(), "Data de adição nula deve ser inválida");
    }

    @Test
    public void testValidLivroCompleto() {
        Livro livro = createValidLivro();
        assertTrue(livro.isValidForCreation(), "Livro completo e válido deve passar na validação");
    }

    @Test
    public void testValidateBusinessRulesComLivroValido() {
        Livro livro = createValidLivro();
        assertDoesNotThrow(livro::validateBusinessRules, "Livro válido não deve lançar exceção");
    }

    @Test
    public void testValidateBusinessRulesComLivroInvalido() {
        Livro livro = createValidLivro();
        livro.setTitulo(null); // Torna inválido
        assertThrows(IllegalArgumentException.class, livro::validateBusinessRules,
                    "Livro inválido deve lançar IllegalArgumentException");
    }

    @Test
    public void testValidateUpdateRulesComDadosValidos() {
        Livro livro = new Livro();
        livro.setTitulo("Novo Título");
        livro.setPreco(39.99);
        assertTrue(livro.isValidForUpdate(), "Atualização parcial com dados válidos deve ser válida");
    }

    @Test
    public void testValidateUpdateRulesComDadosInvalidos() {
        Livro livro = new Livro();
        livro.setTitulo(""); // Inválido
        assertFalse(livro.isValidForUpdate(), "Atualização com dados inválidos deve ser inválida");
    }

    @Test
    public void testEqualsComMesmoObjeto() {
        Livro livro = createValidLivro();
        assertSame(livro, livro, "Livro deve ser igual a si mesmo");
    }

    @Test
    public void testEqualsComObjetosIguais() {
        Livro livro1 = createValidLivro();
        Livro livro2 = createValidLivro();
        LocalDateTime fixed = LocalDateTime.of(2020, 1, 1, 12, 0, 0);
        livro1.setDataAdicao(fixed);
        livro2.setDataAdicao(fixed);
        assertEquals(livro1, livro2, "Livros com mesmos dados devem ser iguais");
    }

    @Test
    public void testEqualsComObjetoNull() {
        Livro livro = createValidLivro();
        assertNotEquals(null, livro, "Livro não deve ser igual a null");
    }

    @Test
    public void testHashCodeConsistente() {
        Livro livro1 = createValidLivro();
        Livro livro2 = createValidLivro();
        LocalDateTime fixed = LocalDateTime.of(2020, 1, 1, 12, 0, 0);
        livro1.setDataAdicao(fixed);
        livro2.setDataAdicao(fixed);
        assertEquals(livro1.hashCode(), livro2.hashCode(), "Objetos iguais devem ter mesmo hashCode");
    }

    @Test
    public void testToStringNaoNull() {
        Livro livro = createValidLivro();
        assertNotNull(livro.toString(), "toString não deve retornar null");
        assertTrue(livro.toString().contains("Livro{"), "toString deve conter identificador da classe");
    }

    // ===== MÉTODOS AUXILIARES =====

    private Livro createValidLivro() {
        Livro livro = new Livro();
        livro.setId(1);
        livro.setTitulo("Clean Code");
        livro.setIsbn("9780132350884");
        livro.setAutor("Robert C. Martin");
        livro.setEditora("Prentice Hall");
        livro.setDescricao("Um livro sobre programação limpa");
        livro.setAnoPublicacao(Year.of(2008));
        livro.setPaginas(464);
        livro.setPreco(89.90);
        livro.setCapa("capa.jpg");

        // Criar objetos relacionados válidos
        Acabamento acabamento = new Acabamento(1);
        livro.setAcabamento(acabamento);

        Conservacao conservacao = new Conservacao(1);
        livro.setEstadoConservacao(conservacao);

        Categoria categoria = new Categoria();
        categoria.setNome("Tecnologia");
        livro.setCategoria(categoria);

        livro.setDataAdicao(LocalDateTime.now().minusDays(1));

        return livro;
    }
}
