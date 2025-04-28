package school.sptech.hub.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO utilizado para mensagens de erro nas operações relacionadas aos livros")
public class LivroErroResponseSwgDto {
    @Schema(description = "Código do erro", example = "404")
private int status;

    @Schema(description = "Mensagem de erro", example = "Recurso não encontrado")
    private String mensagem;

    @Schema(description = "Timestamp do erro", example = "2025-04-23T14:55:00")
    private String timestamp;

    @Schema(description = "Código do erro", example = "400")
    private int status400;

    @Schema(description = "Mensagem de erro", example = "Recurso estruturado incorretamente")
    private String mensagem400;

    @Schema(description = "Timestamp do erro", example = "2025-04-23T14:55:00")
    private String timestamp400;

    public LivroErroResponseSwgDto(int status, String mensagem, String timestamp) {
        this.status = status;
        this.mensagem = mensagem;
        this.timestamp = timestamp;
    }

    public static VendaErroResponseSwgDto erro404(String detalhe) {
        return new VendaErroResponseSwgDto(
                404,
                detalhe != null ? detalhe : "Recurso não encontrado",
                java.time.LocalDateTime.now().toString()
        );
    }

    public int getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getTimestamp() {
        return timestamp;
    }
}