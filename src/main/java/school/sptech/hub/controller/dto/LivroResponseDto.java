package school.sptech.hub.controller.dto;

import school.sptech.hub.entity.Acabamento;
import school.sptech.hub.entity.Categoria;
import school.sptech.hub.entity.Conservacao;

public class LivroResponseDto {

    private String isbn;

    private String autor;

    private String editora;

    private Integer anoPublicacao;

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
}
