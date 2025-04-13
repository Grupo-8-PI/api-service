package school.sptech.hub.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Reserva {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

private LocalDateTime dtReserva;

private LocalDateTime dt_limite;

@Column(length = 45)
private LocalDateTime statusReserva;

private Integer totalReserva;

@ManyToOne
private List<Usuario> usuarios;

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

    public LocalDateTime getDt_limite() {
        return dt_limite;
    }

    public void setDt_limite(LocalDateTime dt_limite) {
        this.dt_limite = dt_limite;
    }

    public LocalDateTime getStatusReserva() {
        return statusReserva;
    }

    public void setStatusReserva(LocalDateTime statusReserva) {
        this.statusReserva = statusReserva;
    }

    public Integer getTotalReserva() {
        return totalReserva;
    }

    public void setTotalReserva(Integer totalReserva) {
        this.totalReserva = totalReserva;
    }
}
