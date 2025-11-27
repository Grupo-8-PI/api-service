package school.sptech.hub.domain.entity;

import java.util.Objects;

public class Categoria {

    private Integer id;
    private String nome;

    public Categoria() {}

    public Categoria(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public boolean isValidForCreation() {
        return isValidNome(this.nome);
    }

    public boolean isValidForUpdate() {
        return this.nome == null || isValidNome(this.nome);
    }

    private boolean isValidNome(String nome) {
        return nome != null &&
               !nome.trim().isEmpty() &&
               nome.length() <= 45;
    }

    // Método para validar regra de negócio específica
    public void validateBusinessRules() {
        if (!isValidForCreation()) {
            throw new IllegalArgumentException("Dados da categoria não atendem às regras de negócio");
        }
    }

    // Método para aplicar regras de negócio durante atualização
    public void validateUpdateRules() {
        if (!isValidForUpdate()) {
            throw new IllegalArgumentException("Dados de atualização da categoria não atendem às regras de negócio");
        }
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Categoria categoria = (Categoria) obj;
        return Objects.equals(id, categoria.id) &&
               Objects.equals(nome, categoria.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
