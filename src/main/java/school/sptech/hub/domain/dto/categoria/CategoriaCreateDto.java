package school.sptech.hub.domain.dto.categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO utilizado para criação de uma nova categoria")
public class CategoriaCreateDto {

    @Schema(description = "Nome da categoria", example = "Romance")
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 45, message = "O nome deve ter no máximo 45 caracteres")
    private String nome;

    public CategoriaCreateDto() {}

    public CategoriaCreateDto(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
