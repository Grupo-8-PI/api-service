package school.sptech.hub.domain.dto.livro;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.time.Year;

@Schema(description = "DTO para atualização de um livro existente")
public class LivroUpdateDto {

    @Schema(description = "ISBN do livro", example = "978-85-359-2982-4")
    private String isbn;

    @Schema(description = "Título do livro", example = "O Alquimista - Edição Especial")
    @Size(max = 255, message = "O título deve ter no máximo 255 caracteres")
    private String titulo;

    @Schema(description = "Nome do autor", example = "Paulo Coelho")
    @Size(max = 100, message = "O nome do autor deve ter no máximo 100 caracteres")
    private String autor;

    @Schema(description = "Nome da editora", example = "Rocco")
    @Size(max = 100, message = "O nome da editora deve ter no máximo 100 caracteres")
    private String editora;

    @Schema(description = "Ano de publicação", example = "2019")
    private Year anoPublicacao;

    @Schema(description = "Quantidade de páginas do livro", example = "180")
    @Min(value = 1, message = "O número de páginas deve ser maior que 0")
    @Max(value = 10000, message = "O número de páginas deve ser no máximo 10.000")
    private Integer paginas;

    @Schema(description = "ID do acabamento do livro", example = "1", allowableValues = {"1", "2"})
    @Min(value = 1, message = "ID do acabamento deve ser entre 1 e 2")
    @Max(value = 2, message = "ID do acabamento deve ser entre 1 e 2")
    private Integer acabamentoId;

    @Schema(description = "ID do estado de conservação do livro", example = "1", allowableValues = {"1", "2", "3", "4"})
    @Min(value = 1, message = "ID da conservação deve ser entre 1 e 4")
    @Max(value = 4, message = "ID da conservação deve ser entre 1 e 4")
    private Integer conservacaoId;

    @Schema(description = "URL da imagem da capa do livro",
            example = "https://m.media-amazon.com/images/I/51X2XvgZ5PL._SY445_SX342_.jpg")
    private String capa;

    @Schema(description = "Preço do livro", example = "34.90")
    @Positive(message = "O preço deve ser maior que zero")
    private Double preco;

    @Schema(description = "Nome da categoria do livro", example = "Ficção")
    @Size(max = 100, message = "O nome da categoria deve ter no máximo 100 caracteres")
    private String nomeCategoria;

    @Schema(description = "Data e hora em que o livro foi adicionado ao sebo",
            example = "2024-12-05T14:30:00")
    private LocalDateTime dataAdicao;

    @Schema(description = "Descrição/sinopse do livro", example = "Uma história envolvente sobre...")
    @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres")
    private String descricao;

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

    public LocalDateTime getDataAdicao() {
        return dataAdicao;
    }

    public void setDataAdicao(LocalDateTime dataAdicao) {
        this.dataAdicao = dataAdicao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

