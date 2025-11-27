package school.sptech.hub.domain.entity;

import java.util.Objects;

public class Conservacao {

    private Integer id;
    private TipoConservacao tipoConservacao;

    public Conservacao() {}

    public Conservacao(Integer id) {
        this.id = id;
        this.tipoConservacao = TipoConservacao.fromId(id);
    }

    public Conservacao(Integer id, TipoConservacao tipoConservacao) {
        this.id = id;
        this.tipoConservacao = tipoConservacao;
    }

    // Construtor para compatibilidade com código existente
    public Conservacao(Integer id, String estadoConservacao) {
        this.id = id;
        this.tipoConservacao = TipoConservacao.fromDescricao(estadoConservacao);
    }

    // Business Rules - Métodos de validação da entidade de domínio
    public boolean isValidForCreation() {
        return isValidId(this.id) && isValidTipoConservacao(this.tipoConservacao);
    }

    public boolean isValidForUpdate() {
        return this.id == null || (isValidId(this.id) && isValidTipoConservacao(this.tipoConservacao));
    }

    private boolean isValidId(Integer id) {
        return id != null && id >= 1 && id <= 4;
    }

    private boolean isValidTipoConservacao(TipoConservacao tipo) {
        return tipo != null;
    }

    public void validateBusinessRules() {
        if (!isValidForCreation()) {
            throw new IllegalArgumentException("Dados da conservação não atendem às regras de negócio. ID deve estar entre 1 e 4.");
        }
    }

    public void validateUpdateRules() {
        if (!isValidForUpdate()) {
            throw new IllegalArgumentException("Dados de atualização da conservação não atendem às regras de negócio. ID deve estar entre 1 e 4.");
        }
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        if (id != null) {
            this.tipoConservacao = TipoConservacao.fromId(id);
        }
    }

    public TipoConservacao getTipoConservacao() {
        return tipoConservacao;
    }

    public void setTipoConservacao(TipoConservacao tipoConservacao) {
        this.tipoConservacao = tipoConservacao;
        if (tipoConservacao != null) {
            this.id = tipoConservacao.getId(); // Sincroniza o ID automaticamente
        }
        if (tipoConservacao != null) {
            this.id = tipoConservacao.getId();
        }
    }

    // Método de compatibilidade para código existente
    public String getEstadoConservacao() {
        return tipoConservacao != null ? tipoConservacao.getDescricao() : null;
    }

    // Método de compatibilidade para código existente
    public void setEstadoConservacao(String estadoConservacao) {
        if (estadoConservacao != null) {
            this.tipoConservacao = TipoConservacao.fromDescricao(estadoConservacao);
            this.id = this.tipoConservacao.getId();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conservacao that = (Conservacao) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(tipoConservacao, that.tipoConservacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoConservacao);
    }

    @Override
    public String toString() {
        return "Conservacao{" +
                "id=" + id +
                ", tipoConservacao=" + tipoConservacao +
                '}';
    }
}
