package school.sptech.hub.domain.dto.livro;

import java.io.Serializable;

/**
 * DTO para o evento de criação de livro enviado para a fila RabbitMQ.
 * Serializable é necessário para que o RabbitMQ possa serializar/deserializar.
 */
public class LivroCriadoEventDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer livroId;
    private String titulo;
    private String autor;
    private String isbn;

    public LivroCriadoEventDto() {}

    public LivroCriadoEventDto(Integer livroId, String titulo, String autor, String isbn) {
        this.livroId = livroId;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
    }

    // Getters and Setters
    public Integer getLivroId() {
        return livroId;
    }

    public void setLivroId(Integer livroId) {
        this.livroId = livroId;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "LivroCriadoEventDto{" +
                "livroId=" + livroId +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}

