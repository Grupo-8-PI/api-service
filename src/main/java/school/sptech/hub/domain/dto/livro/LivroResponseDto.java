package school.sptech.hub.domain.dto.livro;

import io.swagger.v3.oas.annotations.media.Schema;

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

    @Schema(description = "ID do acabamento do livro", example = "1")
    private Integer acabamentoId;

    @Schema(description = "Tipo de acabamento do livro", example = "BROCHURA")
    private String tipoAcabamento;

    @Schema(description = "ID do estado de conservação do livro", example = "2")
    private Integer conservacaoId;

    @Schema(description = "Estado de conservação do livro", example = "OTIMO")
    private String estadoConservacao;

    @Schema(description = "URL da imagem da capa do livro", example = "https://m.media-amazon.com/images/I/91GAAzBixYL._UF894,1000_QL80_.jpg")
    private String capa;

    @Schema(description = "Preço do livro", example = "49.90")
    private Double preco;

    @Schema(description = "ID da categoria do livro", example = "3")
    private Integer categoriaId;

    @Schema(description = "Nome da categoria do livro", example = "ROMANCE")
    private String nomeCategoria;

    @Schema(description = "Descrição/sinopse do livro", example = "Uma história envolvente sobre...")
    private String descricao;

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

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}

