package school.sptech.hub.entity;


import jakarta.persistence.*;

import java.time.Year;

@Entity
public class Livro {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

@Column(length = 45)
private String isbn;

@Column(length = 45)
private String autor;

@Column(length = 45)
private String editora;

private Year anoPublicacao;

private Integer paginas;

private Integer qtdLivros;

@ManyToOne
private Acabamento acabamento;

@ManyToOne
private Conservacao estadoConservacao;

@Lob
private String capa;

private Double preco;

@ManyToOne
private Categoria categoria;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getQtdLivros() {
        return qtdLivros;
    }

    public void setQtdLivros(Integer qtdLivros) {
        this.qtdLivros = qtdLivros;
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

}
