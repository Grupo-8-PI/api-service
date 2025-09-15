package school.sptech.hub.domain.dto.livro;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.Year;

@Schema(description = "DTO para atualização de um livro existente")
public class LivroUpdateDto {

    @Schema(description = "ISBN do livro", example = "978-3-16-148410-0")
    private String isbn;

    @Schema(description = "Título do livro", example = "Dom Casmurro")
    @Size(max = 255, message = "O título deve ter no máximo 255 caracteres")
    private String titulo;

    @Schema(description = "Nome do autor", example = "Machado de Assis")
    @Size(max = 100, message = "O nome do autor deve ter no máximo 100 caracteres")
    private String autor;

    @Schema(description = "Nome da editora", example = "Companhia das Letras")
    @Size(max = 100, message = "O nome da editora deve ter no máximo 100 caracteres")
    private String editora;

    @Schema(description = "Ano de publicação", example = "2001")
    private Year anoPublicacao;

    @Schema(description = "Quantidade de páginas do livro", example = "320")
    @Min(value = 1, message = "O número de páginas deve ser maior que 0")
    @Max(value = 10000, message = "O número de páginas deve ser no máximo 10.000")
    private Integer paginas;

    @Schema(description = "ID do acabamento do livro", example = "1", allowableValues = {"1", "2"})
    @Min(value = 1, message = "ID do acabamento deve ser entre 1 e 2")
    @Max(value = 2, message = "ID do acabamento deve ser entre 1 e 2")
    private Integer acabamentoId;

    @Schema(description = "ID do estado de conservação do livro", example = "2", allowableValues = {"1", "2", "3", "4"})
    @Min(value = 1, message = "ID da conservação deve ser entre 1 e 4")
    @Max(value = 4, message = "ID da conservação deve ser entre 1 e 4")
    private Integer conservacaoId;

    @Schema(description = "URL da imagem da capa do livro")
    private String capa;

    @Schema(description = "Preço do livro", example = "49.90")
    @Positive(message = "O preço deve ser maior que zero")
    private Double preco;

    @Schema(description = "ID da categoria do livro", example = "3")
    private Integer categoriaId;

    // Getters and Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public Year getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Year anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public Integer getPaginas() {
        return paginas;
    }

    public void setPaginas(Integer paginas) {
        this.paginas = paginas;
    }

    public Integer getAcabamentoId() {
        return acabamentoId;
    }

    public void setAcabamentoId(Integer acabamentoId) {
        this.acabamentoId = acabamentoId;
    }

    public Integer getConservacaoId() {
        return conservacaoId;
    }

    public void setConservacaoId(Integer conservacaoId) {
        this.conservacaoId = conservacaoId;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }
}
