package school.sptech.hub.controller.dto.livro;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta com os dados de um livro incluindo a sinopse")
public class LivroComSinopseResponseDto extends LivroResponseDto {

    @Schema(description = "Sinopse gerada do livro", example = "Sinopse detalhada do livro aqui...")
    private String sinopse;

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
}
