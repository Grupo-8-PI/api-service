package school.sptech.hub.controller.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.Size;
import school.sptech.hub.entity.Acabamento;
import school.sptech.hub.entity.Categoria;
import school.sptech.hub.entity.Conservacao;

import java.time.Year;

public class LivroCreateDto {

    @Size(max = 45)
    private String isbn;

    @Size(max = 45)
    private String autor;

    @Size(max = 45)
    private String editora;

    @Size(max = 45)
    private Year anoPublicacao;


    private Integer paginas;

    private Acabamento acabamento;

    private Conservacao estadoConservacao;

    private String capa;

    private Double preco;

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
}
