package school.sptech.hub.domain.dto.livro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import school.sptech.hub.domain.entity.Acabamento;
import school.sptech.hub.domain.entity.Categoria;
import school.sptech.hub.domain.entity.Conservacao;
import school.sptech.hub.domain.entity.Livro;

import java.time.Year;

class LivroMapperTest {

    private LivroCreateDto livroCreateDto;
    private Livro livro;
    private Acabamento acabamento;
    private Categoria categoria;
    private Conservacao conservacao;

    @BeforeEach
    void setUp() {
        // Setup entities
        acabamento = new Acabamento(1, "Brochura");
        categoria = new Categoria(3, "Romance");
        conservacao = new Conservacao(2, "OTIMO");

        // Setup DTO
        livroCreateDto = new LivroCreateDto();
        livroCreateDto.setIsbn("978-85-359-0277-5");
        livroCreateDto.setTitulo("Dom Casmurro");
        livroCreateDto.setAutor("Machado de Assis");
        livroCreateDto.setEditora("Companhia das Letras");
        livroCreateDto.setAnoPublicacao(Year.of(2008));
        livroCreateDto.setPaginas(256);
        livroCreateDto.setAcabamentoId(1);
        livroCreateDto.setConservacaoId(2);
        livroCreateDto.setCapa("https://example.com/capa.jpg");
        livroCreateDto.setPreco(29.90);
        livroCreateDto.setCategoriaId(3);

        // Setup Livro entity
        livro = new Livro(
            1,
            "Dom Casmurro",
            "978-85-359-0277-5",
            "Machado de Assis",
            "Companhia das Letras",
            Year.of(2008),
            256,
            acabamento,
            conservacao,
            "https://example.com/capa.jpg",
            29.90,
            categoria
        );
    }

    @Test
    void testToEntity_Success() {
        // When
        Livro result = LivroMapper.toEntity(livroCreateDto, acabamento, conservacao, categoria);

        // Then
        assertNotNull(result);
        assertEquals(livroCreateDto.getIsbn(), result.getIsbn());
        assertEquals(livroCreateDto.getTitulo(), result.getTitulo());
        assertEquals(livroCreateDto.getAutor(), result.getAutor());
        assertEquals(livroCreateDto.getEditora(), result.getEditora());
        assertEquals(livroCreateDto.getAnoPublicacao(), result.getAnoPublicacao());
        assertEquals(livroCreateDto.getPaginas(), result.getPaginas());
        assertEquals(livroCreateDto.getCapa(), result.getCapa());
        assertEquals(livroCreateDto.getPreco(), result.getPreco());
        assertEquals(acabamento, result.getAcabamento());
        assertEquals(conservacao, result.getEstadoConservacao());
        assertEquals(categoria, result.getCategoria());
    }

    @Test
    void testToEntity_NullDto() {
        // When
        Livro result = LivroMapper.toEntity(null, acabamento, conservacao, categoria);

        // Then
        assertNull(result);
    }

    @Test
    void testToResponseDto_Success() {
        // When
        LivroResponseDto result = LivroMapper.toResponseDto(livro);

        // Then
        assertNotNull(result);
        assertEquals(livro.getId(), result.getId());
        assertEquals(livro.getIsbn(), result.getIsbn());
        assertEquals(livro.getTitulo(), result.getTitulo());
        assertEquals(livro.getAutor(), result.getAutor());
        assertEquals(livro.getEditora(), result.getEditora());
        assertEquals(livro.getAnoPublicacao().getValue(), result.getAnoPublicacao());
        assertEquals(livro.getPaginas(), result.getPaginas());
        assertEquals(livro.getCapa(), result.getCapa());
        assertEquals(livro.getPreco(), result.getPreco());

        // Verificar mapeamento de relacionamentos
        assertEquals(livro.getAcabamento().getId(), result.getAcabamentoId());
        assertEquals(livro.getAcabamento().getTipoAcabamento(), result.getTipoAcabamento());
        assertEquals(livro.getEstadoConservacao().getId(), result.getConservacaoId());
        assertEquals(livro.getEstadoConservacao().getEstadoConservacao(), result.getEstadoConservacao());
        assertEquals(livro.getCategoria().getId(), result.getCategoriaId());
        assertEquals(livro.getCategoria().getNome(), result.getNomeCategoria());
    }

    @Test
    void testToResponseDto_NullLivro() {
        // When
        LivroResponseDto result = LivroMapper.toResponseDto(null);

        // Then
        assertNull(result);
    }

    @Test
    void testToResponseDto_NullRelationships() {
        // Given
        Livro livroSemRelacionamentos = new Livro();
        livroSemRelacionamentos.setId(1);
        livroSemRelacionamentos.setTitulo("Teste");
        livroSemRelacionamentos.setIsbn("123456789");
        livroSemRelacionamentos.setAutor("Autor Teste");
        livroSemRelacionamentos.setEditora("Editora Teste");
        livroSemRelacionamentos.setAnoPublicacao(Year.of(2023));
        livroSemRelacionamentos.setPaginas(100);
        livroSemRelacionamentos.setCapa("capa.jpg");
        livroSemRelacionamentos.setPreco(20.0);

        // When
        LivroResponseDto result = LivroMapper.toResponseDto(livroSemRelacionamentos);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Teste", result.getTitulo());
        assertNull(result.getAcabamentoId());
        assertNull(result.getTipoAcabamento());
        assertNull(result.getConservacaoId());
        assertNull(result.getEstadoConservacao());
        assertNull(result.getCategoriaId());
        assertNull(result.getNomeCategoria());
    }

    @Test
    void testToComSinopseResponseDto_Success() {
        // Given
        String sinopse = "Uma obra-prima da literatura brasileira";

        // When
        LivroComSinopseResponseDto result = LivroMapper.toComSinopseResponseDto(livro, sinopse);

        // Then
        assertNotNull(result);
        assertEquals(livro.getId(), result.getId());
        assertEquals(livro.getTitulo(), result.getTitulo());
        assertEquals(livro.getIsbn(), result.getIsbn());
        assertEquals(sinopse, result.getSinopse());

        // Verificar se herda corretamente do LivroResponseDto
        assertEquals(livro.getAcabamento().getId(), result.getAcabamentoId());
        assertEquals(livro.getCategoria().getNome(), result.getNomeCategoria());
    }

    @Test
    void testToComSinopseResponseDto_NullLivro() {
        // When
        LivroComSinopseResponseDto result = LivroMapper.toComSinopseResponseDto(null, "sinopse");

        // Then
        assertNull(result);
    }

    @Test
    void testFromUpdateDto_Success() {
        // Given
        LivroUpdateDto updateDto = new LivroUpdateDto();
        updateDto.setTitulo("Novo Título");
        updateDto.setPreco(39.90);
        updateDto.setAcabamentoId(1);

        // When
        Livro result = LivroMapper.fromUpdateDto(updateDto, acabamento, conservacao, categoria);

        // Then
        assertNotNull(result);
        assertEquals("Novo Título", result.getTitulo());
        assertEquals(39.90, result.getPreco());
        assertEquals(acabamento, result.getAcabamento());
        assertEquals(conservacao, result.getEstadoConservacao());
        assertEquals(categoria, result.getCategoria());
    }

    @Test
    void testFromUpdateDto_NullDto() {
        // When
        Livro result = LivroMapper.fromUpdateDto(null, acabamento, conservacao, categoria);

        // Then
        assertNull(result);
    }

    @Test
    void testUpdateLivroFields_Success() {
        // Given
        Livro existingLivro = new Livro();
        existingLivro.setId(1);
        existingLivro.setTitulo("Título Original");
        existingLivro.setAutor("Autor Original");
        existingLivro.setPreco(20.0);

        Livro updatedLivro = new Livro();
        updatedLivro.setTitulo("Título Atualizado");
        updatedLivro.setPreco(30.0);

        // When
        Livro result = LivroMapper.updateLivroFields(existingLivro, updatedLivro);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId()); // ID não deve mudar
        assertEquals("Título Atualizado", result.getTitulo()); // Deve ser atualizado
        assertEquals("Autor Original", result.getAutor()); // Não foi fornecido, mantém original
        assertEquals(30.0, result.getPreco()); // Deve ser atualizado
    }

    @Test
    void testUpdateLivroFields_NullExistingLivro() {
        // Given
        Livro updatedLivro = new Livro();
        updatedLivro.setTitulo("Título");

        // When
        Livro result = LivroMapper.updateLivroFields(null, updatedLivro);

        // Then
        assertNull(result);
    }

    @Test
    void testUpdateLivroFields_NullUpdatedLivro() {
        // Given
        Livro existingLivro = new Livro();
        existingLivro.setTitulo("Título Original");

        // When
        Livro result = LivroMapper.updateLivroFields(existingLivro, null);

        // Then
        assertEquals(existingLivro, result);
    }
}
