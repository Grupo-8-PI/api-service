package school.sptech.hub.infraestructure.persistance.acabamentoPersistance;

import jakarta.persistence.*;
import school.sptech.hub.domain.entity.TipoAcabamento;

import java.util.Objects;

@Entity
@Table(name = "acabamento")
public class AcabamentoEntity {

    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 45)
    private TipoAcabamento tipoAcabamento;

    public AcabamentoEntity() {}

    public AcabamentoEntity(Integer id, TipoAcabamento tipoAcabamento) {
        this.id = id;
        this.tipoAcabamento = tipoAcabamento;
    }

    // Construtor para compatibilidade
    public AcabamentoEntity(Integer id, String tipoAcabamentoStr) {
        this.id = id;
        this.tipoAcabamento = TipoAcabamento.fromDescricao(tipoAcabamentoStr);
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoAcabamento getTipoAcabamento() {
        return tipoAcabamento;
    }

    public void setTipoAcabamento(TipoAcabamento tipoAcabamento) {
        this.tipoAcabamento = tipoAcabamento;
    }

    // Método de compatibilidade
    public String getTipoAcabamentoStr() {
        return tipoAcabamento != null ? tipoAcabamento.getDescricao() : null;
    }

    // Método de compatibilidade
    public void setTipoAcabamentoStr(String tipoAcabamentoStr) {
        if (tipoAcabamentoStr != null) {
            this.tipoAcabamento = TipoAcabamento.fromDescricao(tipoAcabamentoStr);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcabamentoEntity that = (AcabamentoEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(tipoAcabamento, that.tipoAcabamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoAcabamento);
    }

    @Override
    public String toString() {
        return "AcabamentoEntity{" +
                "id=" + id +
                ", tipoAcabamento=" + tipoAcabamento +
                '}';
    }
}
