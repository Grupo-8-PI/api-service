package school.sptech.hub.domain.dto.livro;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO utilizado para mensagens de erro nas operações relacionadas aos livros")
public class LivroErroResponseSwgDto {

    @Schema(description = "Código do erro", example = "404")
    private int status;

    @Schema(description = "Mensagem de erro", example = "Recurso não encontrado")
    private String mensagem;

    @Schema(description = "Timestamp do erro", example = "2025-04-23T14:55:00")
    private String timestamp;

    public LivroErroResponseSwgDto() {}

    public LivroErroResponseSwgDto(int status, String mensagem, String timestamp) {
        this.status = status;
        this.mensagem = mensagem;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}