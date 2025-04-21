package school.sptech.hub.entity;

import jakarta.persistence.*;

@Entity
public class Acabamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45)
    private String tipoAcabamento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoAcabamento() {
        return tipoAcabamento;
    }

    public void setTipoAcabamento(String tipoAcabamento) {
        this.tipoAcabamento = tipoAcabamento;
    }
}
