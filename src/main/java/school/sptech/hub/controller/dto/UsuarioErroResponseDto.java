package school.sptech.hub.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Resposta de erro detalhada")
public class UsuarioErroResponseDto {

    @Schema(description = "Código de status HTTP", example = "400")
    private int status;

    @Schema(description = "Tipo do erro", example = "Erro de validação")
    private String erro;

    @Schema(description = "Descrição do erro ocorrido", example = "email: Formato de email inválido; senha: A senha é obrigatória")
    private String mensagem;

    @Schema(description = "Momento em que o erro ocorreu", example = "2025-04-21T15:30:45")
    private LocalDateTime timestamp;

    public UsuarioErroResponseDto(int status, String erro, String mensagem, LocalDateTime timestamp) {
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getErro() {
        return erro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}