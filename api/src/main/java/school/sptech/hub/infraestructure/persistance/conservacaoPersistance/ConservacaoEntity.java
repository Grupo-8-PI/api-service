package school.sptech.hub.infraestructure.persistance.conservacaoPersistance;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "conservacao")
public class ConservacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 45)
    @NotBlank(message = "O estado de conservação é obrigatório")
    @Size(max = 45, message = "O estado de conservação deve ter no máximo 45 caracteres")
    private String estadoConservacao;

    public ConservacaoEntity() {}

    public ConservacaoEntity(Integer id, String estadoConservacao) {
        this.id = id;
        this.estadoConservacao = estadoConservacao;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstadoConservacao() {
        return estadoConservacao;
    }

    public void setEstadoConservacao(String estadoConservacao) {
        this.estadoConservacao = estadoConservacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConservacaoEntity that = (ConservacaoEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(estadoConservacao, that.estadoConservacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, estadoConservacao);
    }

    @Override
    public String toString() {
        return "ConservacaoEntity{" +
                "id=" + id +
                ", estadoConservacao='" + estadoConservacao + '\'' +
                '}';
    }
}
