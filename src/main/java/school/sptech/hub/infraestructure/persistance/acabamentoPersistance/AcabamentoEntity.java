package school.sptech.hub.infraestructure.persistance.acabamentoPersistance;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "acabamento")
public class AcabamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 45)
    @NotBlank(message = "O tipo de acabamento é obrigatório")
    @Size(max = 45, message = "O tipo de acabamento deve ter no máximo 45 caracteres")
    private String tipoAcabamento;

    public AcabamentoEntity() {}

    public AcabamentoEntity(Integer id, String tipoAcabamento) {
        this.id = id;
        this.tipoAcabamento = tipoAcabamento;
    }

    // Getters and Setters
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
                ", tipoAcabamento='" + tipoAcabamento + '\'' +
                '}';
    }
}
