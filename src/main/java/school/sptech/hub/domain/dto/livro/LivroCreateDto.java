package school.sptech.hub.domain.dto.livro;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import school.sptech.hub.domain.entity.Categoria;

import java.time.Year;

@Schema(description = "DTO para cadastro de um novo livro")
public class LivroCreateDto {

    @Schema(description = "ISBN do livro", example = "978-3-16-148410-0")
    @NotBlank(message = "O ISBN é obrigatório")
    @Size(max = 45, message = "O ISBN deve ter no máximo 45 caracteres")
    private String isbn;

    @Schema(description = "Título do livro", example = "Dom Casmurro")
    @NotBlank(message = "O título é obrigatório")
    @Size(max = 255, message = "O título deve ter no máximo 255 caracteres")
    private String titulo;

    @Schema(description = "Nome do autor", example = "Machado de Assis")
    @NotBlank(message = "O nome do autor é obrigatório")
    @Size(max = 100, message = "O nome do autor deve ter no máximo 100 caracteres")
    private String autor;

    @Schema(description = "Nome da editora", example = "Companhia das Letras")
    @NotBlank(message = "O nome da editora é obrigatório")
    @Size(max = 100, message = "O nome da editora deve ter no máximo 100 caracteres")
    private String editora;

    @Schema(description = "Ano de publicação", example = "2001")
    @NotNull(message = "O ano de publicação é obrigatório")
    private Year anoPublicacao;

    @Schema(description = "Quantidade de páginas do livro", example = "320")
    @NotNull(message = "O número de páginas é obrigatório")
    @Min(value = 1, message = "O número de páginas deve ser maior que 0")
    @Max(value = 10000, message = "O número de páginas deve ser no máximo 10.000")
    private Integer paginas;

    @Schema(description = "ID do tipo de acabamento do livro", example = "1", allowableValues = {"1", "2"})
    @NotNull(message = "O ID do acabamento é obrigatório")
    @Min(value = 1, message = "ID do acabamento deve ser entre 1 e 2")
    @Max(value = 2, message = "ID do acabamento deve ser entre 1 e 2")
    private Integer acabamentoId;

    @Schema(description = "ID do estado de conservação do livro", example = "1", allowableValues = {"1", "2", "3", "4"})
    @NotNull(message = "O ID do estado de conservação é obrigatório")
    @Min(value = 1, message = "ID da conservação deve ser entre 1 e 4")
    @Max(value = 4, message = "ID da conservação deve ser entre 1 e 4")
    private Integer conservacaoId;

    @Schema(description = "URL da imagem da capa do livro", example = "https://m.media-amazon.com/images/I/91GAAzBixYL._UF894,1000_QL80_.jpg")
    @NotBlank(message = "A capa é obrigatória")
    private String capa;

    @Schema(description = "Preço do livro", example = "49.90")
    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    private Double preco;

    @Schema(description = "Nome da categoria do livro", example = "Romance")
    @NotBlank(message = "O nome da categoria é obrigatório")
    @Size(max = 100, message = "O nome da categoria deve ter no máximo 100 caracteres")
    private String nomeCategoria;

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

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
}
