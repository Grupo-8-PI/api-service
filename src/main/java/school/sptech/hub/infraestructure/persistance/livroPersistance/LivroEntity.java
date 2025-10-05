package school.sptech.hub.infraestructure.persistance.livroPersistance;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import school.sptech.hub.infraestructure.persistance.acabamentoPersistance.AcabamentoEntity;
import school.sptech.hub.infraestructure.persistance.categoriaPersistance.CategoriaEntity;
import school.sptech.hub.infraestructure.persistance.conservacaoPersistance.ConservacaoEntity;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.Objects;

@Entity
@Table(name = "livro")
public class LivroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 60)
    @NotBlank
    private String titulo;

    @Column(nullable = false, unique = true, length = 45)
    @NotBlank
    private String isbn;

    @Column(nullable = false, length = 45)
    @NotBlank
    private String autor;

    @Column(nullable = false, length = 45)
    @NotBlank
    private String editora;

    @Column(nullable = false)
    @NotNull
    private Year anoPublicacao;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    private Integer paginas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acabamento_id", nullable = false)
    @NotNull
    private AcabamentoEntity acabamento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "conservacao_id", nullable = false)
    @NotNull
    private ConservacaoEntity estadoConservacao;

    @Lob
    @Column(nullable = true)  // Permitir nulo durante criação
    private String capa;

    @Column(nullable = false)
    @NotNull
    @Positive
    private Double preco;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    @NotNull
    private CategoriaEntity categoria;

    @Column(name = "data_adicao", nullable = false)
    @NotNull
    private LocalDateTime dataAdicao;

    public LivroEntity() {}

    public LivroEntity(Integer id, String titulo, String isbn, String autor, String editora,
                       Year anoPublicacao, Integer paginas, AcabamentoEntity acabamento,
                       ConservacaoEntity estadoConservacao, String capa, Double preco,
                       CategoriaEntity categoria, LocalDateTime dataAdicao) {
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

    // Getters and Setters
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

    public AcabamentoEntity getAcabamento() { return acabamento; }
    public void setAcabamento(AcabamentoEntity acabamento) { this.acabamento = acabamento; }

    public ConservacaoEntity getEstadoConservacao() { return estadoConservacao; }
    public void setEstadoConservacao(ConservacaoEntity estadoConservacao) { this.estadoConservacao = estadoConservacao; }

    public String getCapa() { return capa; }
    public void setCapa(String capa) { this.capa = capa; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public CategoriaEntity getCategoria() { return categoria; }
    public void setCategoria(CategoriaEntity categoria) { this.categoria = categoria; }

    public LocalDateTime getDataAdicao() {
        return dataAdicao;
    }

    public void setDataAdicao(LocalDateTime dataAdicao) {
        this.dataAdicao = dataAdicao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LivroEntity that = (LivroEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(titulo, that.titulo) &&
                Objects.equals(isbn, that.isbn) &&
                Objects.equals(autor, that.autor) &&
                Objects.equals(editora, that.editora) &&
                Objects.equals(anoPublicacao, that.anoPublicacao) &&
                Objects.equals(paginas, that.paginas) &&
                Objects.equals(acabamento, that.acabamento) &&
                Objects.equals(estadoConservacao, that.estadoConservacao) &&
                Objects.equals(capa, that.capa) &&
                Objects.equals(preco, that.preco) &&
                Objects.equals(categoria, that.categoria) &&
                Objects.equals(dataAdicao, that.dataAdicao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, isbn, autor, editora, anoPublicacao, paginas,
                           acabamento, estadoConservacao, capa, preco, categoria, dataAdicao);
    }

    @Override
    public String toString() {
        return "LivroEntity{" +
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
