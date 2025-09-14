package school.sptech.hub.infraestructure.persistance.vendaPersistance;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "venda")
public class VendaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime dtReserva;

    @Column(nullable = false)
    private LocalDateTime dtLimite;

    @Column(nullable = false, length = 50)
    private String statusReserva;

    @Column(nullable = false)
    private Integer totalReserva;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioEntity usuarios;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public LocalDateTime getDtReserva() { return dtReserva; }
    public void setDtReserva(LocalDateTime dtReserva) { this.dtReserva = dtReserva; }
    public LocalDateTime getDtLimite() { return dtLimite; }
    public void setDtLimite(LocalDateTime dtLimite) { this.dtLimite = dtLimite; }
    public String getStatusReserva() { return statusReserva; }
    public void setStatusReserva(String statusReserva) { this.statusReserva = statusReserva; }
    public Integer getTotalReserva() { return totalReserva; }
    public void setTotalReserva(Integer totalReserva) { this.totalReserva = totalReserva; }
    public school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioEntity getUsuarios() { return usuarios; }
    public void setUsuarios(school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioEntity usuarios) { this.usuarios = usuarios; }
}

