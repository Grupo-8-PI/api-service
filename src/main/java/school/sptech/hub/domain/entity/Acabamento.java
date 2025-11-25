package school.sptech.hub.domain.entity;

import java.util.Objects;

public class Acabamento {

    private Integer id;
    private TipoAcabamento tipoAcabamento;

    public Acabamento() {}

    public Acabamento(Integer id) {
        this.id = id;
        this.tipoAcabamento = TipoAcabamento.fromId(id);
    }

    public Acabamento(Integer id, TipoAcabamento tipoAcabamento) {
        this.id = id;
        this.tipoAcabamento = tipoAcabamento;
    }

    // Construtor para compatibilidade com código existente
    public Acabamento(Integer id, String tipoAcabamentoStr) {
        this.id = id;
        this.tipoAcabamento = TipoAcabamento.fromDescricao(tipoAcabamentoStr);
    }

    // Business Rules - Métodos de validação da entidade de domínio
    public boolean isValidForCreation() {
        return isValidId(this.id) && isValidTipoAcabamento(this.tipoAcabamento);
    }

    public boolean isValidForUpdate() {
        return this.id == null || (isValidId(this.id) && isValidTipoAcabamento(this.tipoAcabamento));
    }

    private boolean isValidId(Integer id) {
        return id != null && id >= 1 && id <= 2;
    }

    private boolean isValidTipoAcabamento(TipoAcabamento tipo) {
        return tipo != null;
    }

    public void validateBusinessRules() {
        if (!isValidForCreation()) {
            throw new IllegalArgumentException("Dados do acabamento não atendem às regras de negócio. ID deve estar entre 1 e 2.");
        }
    }

    public void validateUpdateRules() {
        if (!isValidForUpdate()) {
            throw new IllegalArgumentException("Dados de atualização do acabamento não atendem às regras de negócio. ID deve estar entre 1 e 2.");
        }
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        if (id != null) {
            this.tipoAcabamento = TipoAcabamento.fromId(id);
        }
    }

    public TipoAcabamento getTipoAcabamento() {
        return tipoAcabamento;
    }

    public void setTipoAcabamento(TipoAcabamento tipoAcabamento) {
        this.tipoAcabamento = tipoAcabamento;
        if (tipoAcabamento != null) {
            this.id = tipoAcabamento.getId();
        }
    }

    // Método de compatibilidade para código existente
    public String getTipoAcabamentoStr() {
        return tipoAcabamento != null ? tipoAcabamento.getDescricao() : null;
    }

    // Método de compatibilidade para código existente
    public void setTipoAcabamentoStr(String tipoAcabamentoStr) {
        if (tipoAcabamentoStr != null) {
            this.tipoAcabamento = TipoAcabamento.fromDescricao(tipoAcabamentoStr);
            this.id = this.tipoAcabamento.getId();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Acabamento that = (Acabamento) obj;
        return Objects.equals(id, that.id) &&
               Objects.equals(tipoAcabamento, that.tipoAcabamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoAcabamento);
    }

    @Override
    public String toString() {
        return "Acabamento{" +
                "id=" + id +
                ", tipoAcabamento=" + tipoAcabamento +
                '}';
    }
}
