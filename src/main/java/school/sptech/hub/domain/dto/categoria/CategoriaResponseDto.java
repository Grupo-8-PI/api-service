package school.sptech.hub.domain.dto.categoria;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta para dados de categoria")
public class CategoriaResponseDto {

    @Schema(description = "ID da categoria", example = "1")
    private Integer id;

    @Schema(description = "Nome da categoria", example = "Romance")
    private String nome;

    public CategoriaResponseDto() {}

    public CategoriaResponseDto(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
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
}
