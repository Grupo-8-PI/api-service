package school.sptech.hub.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime dtReserva;

    private LocalDateTime dtLimite;

    @Column(length = 45)
    private String statusReserva;

    public LocalDateTime getDtLimite() {
        return dtLimite;
    }

    public void setDtLimite(LocalDateTime dtLimite) {
        this.dtLimite = dtLimite;
    }

    private Integer totalReserva;

    @ManyToOne
    private Usuario usuarios;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDtReserva() {
        return dtReserva;
    }

    public void setDtReserva(LocalDateTime dtReserva) {
        this.dtReserva = dtReserva;
    }

    public String  getStatusReserva() {
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
}
