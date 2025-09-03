package school.sptech.hub.domain.dto.livro;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import school.sptech.hub.domain.entity.Acabamento;
import school.sptech.hub.domain.entity.Categoria;
import school.sptech.hub.domain.entity.Conservacao;

import java.time.Year;

@Schema(description = "DTO para cadastro de um novo livro")
public class LivroCreateDto {

    @Schema(description = "ISBN do livro", example = "978-3-16-148410-0")
    @NotBlank(message = "O ISBN é obrigatório")
    @Size(max = 45, message = "O ISBN deve ter no máximo 45 caracteres")
    private String isbn;

    @Schema(description = "Título do livro", example = "Dom Casmurro")
    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @Schema(description = "Nome do autor", example = "Machado de Assis")
    @NotBlank(message = "O nome do autor é obrigatório")
    @Size(max = 45, message = "O nome do autor deve ter no máximo 45 caracteres")
    private String autor;

    @Schema(description = "Nome da editora", example = "Companhia das Letras")
    @NotBlank(message = "O nome da editora é obrigatório")
    @Size(max = 45, message = "O nome da editora deve ter no máximo 45 caracteres")
    private String editora;

    @Schema(description = "Ano de publicação", example = "2001")
    @NotNull(message = "O ano de publicação é obrigatório")
    private Year anoPublicacao;

    @Schema(description = "Quantidade de páginas do livro", example = "320")
    @NotNull(message = "O número de páginas é obrigatório")
    @Min(value = 1, message = "O número de páginas deve ser maior que 0")
    private Integer paginas;

    @Schema(description = "Tipo de acabamento do livro")
    @NotNull(message = "O acabamento é obrigatório")
    private Acabamento acabamento;

    @Schema(description = "Estado de conservação do livro")
    @NotNull(message = "O estado de conservação é obrigatório")
    private Conservacao estadoConservacao;

    @Schema(description = "URL da imagem da capa do livro", example = "https://m.media-amazon.com/images/I/91GAAzBixYL._UF894,1000_QL80_.jpg")
    @NotBlank(message = "A capa é obrigatória")
    private String capa;

    @Schema(description = "Preço do livro", example = "49.90")
    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    private Double preco;

    @Schema(description = "Categoria do livro")
    @NotNull(message = "A categoria é obrigatória")
    private Categoria categoria;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public Acabamento getAcabamento() {
        return acabamento;
    }

    public void setAcabamento(Acabamento acabamento) {
        this.acabamento = acabamento;
    }

    public Conservacao getEstadoConservacao() {
        return estadoConservacao;
    }

    public void setEstadoConservacao(Conservacao estadoConservacao) {
        this.estadoConservacao = estadoConservacao;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
