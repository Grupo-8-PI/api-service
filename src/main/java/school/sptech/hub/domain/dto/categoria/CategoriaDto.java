package school.sptech.hub.domain.dto.categoria;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO contendo informações básicas de uma categoria")
public class CategoriaDto {

    @Schema(description = "ID único da categoria", example = "1")
    private Integer id;

    @Schema(description = "Nome da categoria", example = "Ficção Cientifica")
    private String nome;

    public CategoriaDto() {
    }

    public CategoriaDto(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

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
}

