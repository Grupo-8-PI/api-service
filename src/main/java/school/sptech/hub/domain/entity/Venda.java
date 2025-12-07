package school.sptech.hub.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
public class Venda {
    private Integer id;
    private LocalDateTime dtReserva;
    private LocalDateTime dtLimite;
    private String statusReserva;
    private Integer totalReserva;
    private Usuario usuarios;
    private Livro livro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDtReserva() {
        return dtReserva;
    }

    public void setDtReserva(LocalDateTime dtReserva) {
        this.dtReserva = dtReserva;
    }

    public LocalDateTime getDtLimite() {
        return dtLimite;
    }

    public void setDtLimite(LocalDateTime dtLimite) {
        this.dtLimite = dtLimite;
    }

    public String getStatusReserva() {
        return statusReserva;
    }

    public void setStatusReserva(String statusReserva) {
        this.statusReserva = statusReserva;
    }

    public Integer getTotalReserva() {
        return totalReserva;
    }

    public void setTotalReserva(Integer totalReserva) {
        this.totalReserva = totalReserva;
    }

    public Usuario getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuario usuarios) {
        this.usuarios = usuarios;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return Objects.equals(id, venda.id) &&
                Objects.equals(dtReserva, venda.dtReserva) &&
                Objects.equals(dtLimite, venda.dtLimite) &&
                Objects.equals(statusReserva, venda.statusReserva) &&
                Objects.equals(totalReserva, venda.totalReserva) &&
                Objects.equals(usuarios, venda.usuarios) &&
                Objects.equals(livro, venda.livro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dtReserva, dtLimite, statusReserva, totalReserva, usuarios, livro);
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", dtReserva=" + dtReserva +
                ", dtLimite=" + dtLimite +
                ", statusReserva='" + statusReserva + '\'' +
                ", totalReserva=" + totalReserva +
                ", usuarios=" + usuarios +
                ", livro=" + livro +
                '}';
    }
}