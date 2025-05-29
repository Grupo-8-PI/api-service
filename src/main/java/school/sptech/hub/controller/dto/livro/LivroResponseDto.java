package school.sptech.hub.controller.dto.livro;

import io.swagger.v3.oas.annotations.media.Schema;
import school.sptech.hub.entity.Acabamento;
import school.sptech.hub.entity.Categoria;
import school.sptech.hub.entity.Conservacao;

@Schema(description = "DTO de resposta com os dados de um livro")
public class LivroResponseDto {

    @Schema(description = "ID do Livro", example = "2")
    private Integer id;

    @Schema(description = "ISBN do livro", example = "978-3-16-148410-0")
    private String isbn;

    @Schema(description = "Título do livro", example = "Dom Casmurro")
    private String titulo;

    @Schema(description = "Nome do autor do livro", example = "Machado de Assis")
    private String autor;

    @Schema(description = "Nome da editora do livro", example = "Companhia das Letras")
    private String editora;

    @Schema(description = "Ano de publicação do livro", example = "2001")
    private Integer anoPublicacao;

    @Schema(description = "Número de páginas do livro", example = "320")
    private Integer paginas;

    @Schema(description = "Tipo de acabamento do livro", example = "BROCHURA")
    private Acabamento acabamento;

    @Schema(description = "Estado de conservação do livro", example = "OTIMO")
    private Conservacao estadoConservacao;

    @Schema(description = "URL da imagem da capa do livro", example = "https://m.media-amazon.com/images/I/91GAAzBixYL._UF894,1000_QL80_.jpg")
    private String capa;

    @Schema(description = "Preço do livro", example = "49.90")
    private Double preco;

    @Schema(description = "Categoria do livro", example = "ROMANCE")
    private Categoria categoria;


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

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

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
