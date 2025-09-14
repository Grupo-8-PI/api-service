package school.sptech.hub.domain.entity;

import java.util.Objects;

public class Acabamento {

    private Integer id;
    private String tipoAcabamento;

    public Acabamento() {}

    public Acabamento(Integer id, String tipoAcabamento) {
        this.id = id;
        this.tipoAcabamento = tipoAcabamento;
    }

    // Business Rules - Métodos de validação da entidade de domínio
    public boolean isValidForCreation() {
        return isValidTipoAcabamento(this.tipoAcabamento);
    }

    public boolean isValidForUpdate() {
        // Para update, o campo pode ser nulo (update parcial)
        // Mas se fornecido, deve ser válido
        return this.tipoAcabamento == null || isValidTipoAcabamento(this.tipoAcabamento);
    }

    private boolean isValidTipoAcabamento(String tipoAcabamento) {
        return tipoAcabamento != null &&
               !tipoAcabamento.trim().isEmpty() &&
               tipoAcabamento.length() <= 45;
    }

    // Método para validar regra de negócio específica
    public void validateBusinessRules() {
        if (!isValidForCreation()) {
            throw new IllegalArgumentException("Dados do acabamento não atendem às regras de negócio");
        }
    }

    // Método para aplicar regras de negócio durante atualização
    public void validateUpdateRules() {
        if (!isValidForUpdate()) {
            throw new IllegalArgumentException("Dados de atualização do acabamento não atendem às regras de negócio");
        }
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
        Acabamento that = (Acabamento) o;
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
                ", tipoAcabamento='" + tipoAcabamento + '\'' +
                '}';
    }
}
