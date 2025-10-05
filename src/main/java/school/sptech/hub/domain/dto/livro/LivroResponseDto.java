package school.sptech.hub.domain.dto.livro;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "DTO de resposta com os dados de um livro")
public class LivroResponseDto {

    @Schema(description = "ID do Livro", example = "1")
    private Integer id;

    @Schema(description = "ISBN do livro", example = "978-85-359-2982-4")
    private String isbn;

    @Schema(description = "Título do livro", example = "O Alquimista")
    private String titulo;

    @Schema(description = "Nome do autor do livro", example = "Paulo Coelho")
    private String autor;

    @Schema(description = "Nome da editora do livro", example = "Rocco")
    private String editora;

    @Schema(description = "Ano de publicação do livro", example = "2018")
    private Integer anoPublicacao;

    @Schema(description = "Número de páginas do livro", example = "174")
    private Integer paginas;

    @Schema(description = "ID do acabamento do livro", example = "2")
    private Integer acabamentoId;

    @Schema(description = "Tipo de acabamento do livro", example = "BROCHURA")
    private String tipoAcabamento;

    @Schema(description = "ID do estado de conservação do livro", example = "2")
    private Integer conservacaoId;

    @Schema(description = "Estado de conservação do livro", example = "BOM")
    private String estadoConservacao;

    @Schema(description = "URL da imagem da capa do livro",
            example = "https://m.media-amazon.com/images/I/51X2XvgZ5PL._SY445_SX342_.jpg")
    private String capa;

    @Schema(description = "Preço do livro", example = "29.90")
    private Double preco;

    @Schema(description = "ID da categoria do livro", example = "1")
    private Integer categoriaId;

    @Schema(description = "Nome da categoria do livro", example = "Romance")
    private String nomeCategoria;

    @Schema(description = "Data e hora em que o livro foi adicionado ao sebo",
            example = "2024-12-05T14:30:00")
    private LocalDateTime dataAdicao;


    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }

    public Integer getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(Integer anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    public Integer getPaginas() { return paginas; }
    public void setPaginas(Integer paginas) { this.paginas = paginas; }

    public Integer getAcabamentoId() { return acabamentoId; }
    public void setAcabamentoId(Integer acabamentoId) { this.acabamentoId = acabamentoId; }

    public String getTipoAcabamento() { return tipoAcabamento; }
    public void setTipoAcabamento(String tipoAcabamento) { this.tipoAcabamento = tipoAcabamento; }

    public Integer getConservacaoId() { return conservacaoId; }
    public void setConservacaoId(Integer conservacaoId) { this.conservacaoId = conservacaoId; }

    public String getEstadoConservacao() { return estadoConservacao; }
    public void setEstadoConservacao(String estadoConservacao) { this.estadoConservacao = estadoConservacao; }

    public String getCapa() { return capa; }
    public void setCapa(String capa) { this.capa = capa; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public Integer getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Integer categoriaId) { this.categoriaId = categoriaId; }

    public String getNomeCategoria() { return nomeCategoria; }
    public void setNomeCategoria(String nomeCategoria) { this.nomeCategoria = nomeCategoria; }

    public LocalDateTime getDataAdicao() {
        return dataAdicao;
    }

    public void setDataAdicao(LocalDateTime dataAdicao) {
        this.dataAdicao = dataAdicao;
    }
}
