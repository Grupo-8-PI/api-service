package school.sptech.hub.domain.entity;


import java.time.LocalDateTime;
import java.time.Year;
import java.util.Objects;

public class Livro {

    private Integer id;
    private String titulo;
    private String isbn;
    private String autor;
    private String editora;
    private String descricao;
    private Year anoPublicacao;
    private Integer paginas;
    private Acabamento acabamento;
    private Conservacao estadoConservacao;
    private String capa;
    private Double preco;
    private Categoria categoria;
    private LocalDateTime dataAdicao;

    public Livro() {}

    public Livro(Integer id, String titulo, String isbn, String autor, String editora,
                 Year anoPublicacao, Integer paginas, Acabamento acabamento,
                 Conservacao estadoConservacao, String capa, Double preco, Categoria categoria,
                 LocalDateTime dataAdicao) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.autor = autor;
        this.editora = editora;
        this.anoPublicacao = anoPublicacao;
        this.paginas = paginas;
        this.acabamento = acabamento;
        this.estadoConservacao = estadoConservacao;
        this.capa = capa;
        this.preco = preco;
        this.categoria = categoria;
        this.dataAdicao = dataAdicao;
    }

    public boolean isValidForCreation() {
        return isValidIsbn(this.isbn) &&
               isValidTitulo(this.titulo) &&
               isValidAutor(this.autor) &&
               isValidEditora(this.editora) &&
               isValidAnoPublicacao(this.anoPublicacao) &&
               isValidPaginas(this.paginas) &&
               isValidPreco(this.preco) &&
               isValidAcabamento(this.acabamento) &&
               isValidEstadoConservacao(this.estadoConservacao) &&
               isValidCategoria(this.categoria) &&
               isValidDataAdicao(this.dataAdicao);
               // Capa não é obrigatória na criação - será adicionada via PATCH
    }

    public boolean isValidForUpdate() {
        if (this.isbn != null && !isValidIsbn(this.isbn)) return false;
        if (this.titulo != null && !isValidTitulo(this.titulo)) return false;
        if (this.autor != null && !isValidAutor(this.autor)) return false;
        if (this.editora != null && !isValidEditora(this.editora)) return false;
        if (this.anoPublicacao != null && !isValidAnoPublicacao(this.anoPublicacao)) return false;
        if (this.paginas != null && !isValidPaginas(this.paginas)) return false;
        if (this.preco != null && !isValidPreco(this.preco)) return false;
        if (this.capa != null && !isValidCapa(this.capa)) return false;
        if (this.acabamento != null && !isValidAcabamento(this.acabamento)) return false;
        if (this.estadoConservacao != null && !isValidEstadoConservacao(this.estadoConservacao)) return false;
        if (this.categoria != null && !isValidCategoria(this.categoria)) return false;
        if (this.dataAdicao != null && !isValidDataAdicao(this.dataAdicao)) return false;
        if (this.descricao != null && !isValidDescricao(this.descricao)) return false;

        return true;
    }

    private boolean isValidIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }

        String cleanIsbn = isbn.replaceAll("[\\s-]", "");

        return cleanIsbn.matches("\\d{9}[\\dX]") || cleanIsbn.matches("\\d{13}");
    }

    private boolean isValidAnoPublicacao(Year year) {
        if (year == null) {
            return false;
        }

        Year currentYear = Year.now();
        Year oldestYear = Year.of(1450);

        return !year.isBefore(oldestYear) && !year.isAfter(currentYear);
    }

    private boolean isValidPaginas(Integer pages) {
        return pages != null && pages > 0 && pages <= 10000;
    }

    private boolean isValidPreco(Double price) {
        return price != null && price > 0 && price <= 999999.99;
    }

    private boolean isValidTitulo(String title) {
        return title != null && !title.trim().isEmpty() && title.length() <= 255;
    }

    private boolean isValidAutor(String author) {
        return author != null && !author.trim().isEmpty() && author.length() <= 100;
    }

    private boolean isValidEditora(String editora) {
        return editora != null && !editora.trim().isEmpty() && editora.length() <= 100;
    }

    private boolean isValidCapa(String capa) {
        return capa != null && !capa.trim().isEmpty();
    }

    private boolean isValidAcabamento(Acabamento acabamento) {
        return acabamento != null &&
               acabamento.getId() != null &&
               acabamento.getId() >= 1 &&
               acabamento.getId() <= 2 &&
               acabamento.getTipoAcabamento() != null;
    }

    private boolean isValidEstadoConservacao(Conservacao conservacao) {
        return conservacao != null &&
               conservacao.getId() != null &&
               conservacao.getId() >= 1 &&
               conservacao.getId() <= 4 &&
               conservacao.getTipoConservacao() != null;
    }

    private boolean isValidCategoria(Categoria categoria) {
        return categoria != null &&
               categoria.getNome() != null &&
               !categoria.getNome().trim().isEmpty();
    }

    private boolean isValidDataAdicao(LocalDateTime dataAdicao) {
        if (dataAdicao == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minDate = LocalDateTime.of(2020, 1, 1, 0, 0); // Data mínima razoável para o sebo

        return !dataAdicao.isBefore(minDate) && !dataAdicao.isAfter(now);
    }

    public void validateBusinessRules() {
        if (!isValidForCreation()) {
            throw new IllegalArgumentException("Dados do livro não atendem às regras de negócio");
        }
    }

    public void validateUpdateRules() {
        if (!isValidForUpdate()) {
            throw new IllegalArgumentException("Dados de atualização do livro não atendem às regras de negócio");
        }
    }

    private boolean isValidDescricao(String descricao) {
        return descricao instanceof String;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }

    public Year getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(Year anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    public Integer getPaginas() { return paginas; }
    public void setPaginas(Integer paginas) { this.paginas = paginas; }

    public Acabamento getAcabamento() { return acabamento; }
    public void setAcabamento(Acabamento acabamento) { this.acabamento = acabamento; }

    public Conservacao getEstadoConservacao() { return estadoConservacao; }
    public void setEstadoConservacao(Conservacao estadoConservacao) { this.estadoConservacao = estadoConservacao; }

    public String getCapa() { return capa; }
    public void setCapa(String capa) { this.capa = capa; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public LocalDateTime getDataAdicao() {
        return dataAdicao;
    }

    public void setDataAdicao(LocalDateTime dataAdicao) {
        this.dataAdicao = dataAdicao;
    }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(id, livro.id) &&
                Objects.equals(titulo, livro.titulo) &&
                Objects.equals(isbn, livro.isbn) &&
                Objects.equals(autor, livro.autor) &&
                Objects.equals(editora, livro.editora) &&
                Objects.equals(anoPublicacao, livro.anoPublicacao) &&
                Objects.equals(paginas, livro.paginas) &&
                Objects.equals(acabamento, livro.acabamento) &&
                Objects.equals(estadoConservacao, livro.estadoConservacao) &&
                Objects.equals(capa, livro.capa) &&
                Objects.equals(preco, livro.preco) &&
                Objects.equals(categoria, livro.categoria) &&
                Objects.equals(dataAdicao, livro.dataAdicao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, isbn, autor, editora, anoPublicacao, paginas,
                acabamento, estadoConservacao, capa, preco, categoria, dataAdicao);
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", isbn='" + isbn + '\'' +
                ", autor='" + autor + '\'' +
                ", editora='" + editora + '\'' +
                ", anoPublicacao=" + anoPublicacao +
                ", paginas=" + paginas +
                ", acabamento=" + acabamento +
                ", estadoConservacao=" + estadoConservacao +
                ", capa='" + capa + '\'' +
                ", preco=" + preco +
                ", categoria=" + categoria +
                ", dataAdicao=" + dataAdicao +
                '}';
    }
}

