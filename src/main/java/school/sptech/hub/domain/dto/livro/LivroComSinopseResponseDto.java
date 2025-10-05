package school.sptech.hub.domain.dto.livro;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta com os dados de um livro incluindo a sinopse gerada por IA")
public class LivroComSinopseResponseDto extends LivroResponseDto {

    @Schema(description = "Sinopse gerada por IA do livro",
            example = "Um jovem pastor andaluz parte em uma jornada em busca de um tesouro localizado nas Pirâmides do Egito. Pelo caminho, aprende a ouvir seu coração e descobre que os tesouros mais preciosos encontram-se dentro de nós mesmos. Uma fábula sobre seguir os próprios sonhos e encontrar o sentido da vida.")
    private String sinopse;

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
}
