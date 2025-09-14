package school.sptech.hub.infraestructure.persistance.conservacaoPersistance;

import jakarta.persistence.*;
import school.sptech.hub.domain.entity.TipoConservacao;

import java.util.Objects;

@Entity
@Table(name = "conservacao")
public class ConservacaoEntity {

    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 45)
    private TipoConservacao tipoConservacao;

    public ConservacaoEntity() {}

    public ConservacaoEntity(Integer id, TipoConservacao tipoConservacao) {
        this.id = id;
        this.tipoConservacao = tipoConservacao;
    }

    // Construtor para compatibilidade
    public ConservacaoEntity(Integer id, String estadoConservacao) {
        this.id = id;
        this.tipoConservacao = TipoConservacao.fromDescricao(estadoConservacao);
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoConservacao getTipoConservacao() {
        return tipoConservacao;
    }

    public void setTipoConservacao(TipoConservacao tipoConservacao) {
        this.tipoConservacao = tipoConservacao;
    }

    // Método de compatibilidade
    public String getEstadoConservacao() {
        return tipoConservacao != null ? tipoConservacao.getDescricao() : null;
    }

    // Método de compatibilidade
    public void setEstadoConservacao(String estadoConservacao) {
        if (estadoConservacao != null) {
            this.tipoConservacao = TipoConservacao.fromDescricao(estadoConservacao);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConservacaoEntity that = (ConservacaoEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(tipoConservacao, that.tipoConservacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoConservacao);
    }

    @Override
    public String toString() {
        return "ConservacaoEntity{" +
                "id=" + id +
                ", tipoConservacao=" + tipoConservacao +
                '}';
    }
}
