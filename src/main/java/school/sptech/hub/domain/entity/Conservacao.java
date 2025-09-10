package school.sptech.hub.domain.entity;

import java.util.Objects;

public class Conservacao {

    private Integer id;
    private String estadoConservacao;

    public Conservacao() {}

    public Conservacao(Integer id, String estadoConservacao) {
        this.id = id;
        this.estadoConservacao = estadoConservacao;
    }

    // Business Rules - Métodos de validação da entidade de domínio
    public boolean isValidForCreation() {
        return isValidEstadoConservacao(this.estadoConservacao) &&
               isValidEstadoConservacaoValue();
    }

    public boolean isValidForUpdate() {
        return this.estadoConservacao == null ||
               (isValidEstadoConservacao(this.estadoConservacao) && isValidEstadoConservacaoValue());
    }

    private boolean isValidEstadoConservacao(String estadoConservacao) {
        return estadoConservacao != null &&
               !estadoConservacao.trim().isEmpty() &&
               estadoConservacao.length() <= 45;
    }

    // Método para verificar se o estado de conservação está dentro dos valores aceitos
    private boolean isValidEstadoConservacaoValue() {
        if (this.estadoConservacao == null) return false;

        String estado = this.estadoConservacao.trim().toUpperCase();
        return estado.equals("EXCELENTE") ||
               estado.equals("OTIMO") ||
               estado.equals("BOM") ||
               estado.equals("REGULAR") ||
               estado.equals("RUIM");
    }

    public void validateBusinessRules() {
        if (!isValidForCreation()) {
            throw new IllegalArgumentException("Dados da conservação não atendem às regras de negócio");
        }
    }

    public void validateUpdateRules() {
        if (!isValidForUpdate()) {
            throw new IllegalArgumentException("Dados de atualização da conservação não atendem às regras de negócio");
        }
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
        Conservacao that = (Conservacao) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(estadoConservacao, that.estadoConservacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, estadoConservacao);
    }

    @Override
    public String toString() {
        return "Conservacao{" +
                "id=" + id +
                ", estadoConservacao='" + estadoConservacao + '\'' +
                '}';
    }
}
