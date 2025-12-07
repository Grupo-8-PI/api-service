package school.sptech.hub.domain.dto.venda;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO utilizado para a criação de uma nova venda ou reserva")
public class VendaCreateDto {

    @Schema(description = "Status da reserva", example = "Confirmada")
    @Size(max = 45, message = "O status da reserva deve ter no máximo 45 caracteres")
    private String statusReserva;


    @Schema(description = "ID do livro a ser reservado", example = "10")
    @NotNull(message = "O ID do livro é obrigatório")
    private Integer livroId;


    public String getStatusReserva() {
        return statusReserva;
    }

    public void setStatusReserva(String statusReserva) {
        this.statusReserva = statusReserva;
    }


    public Integer getLivroId() {
        return livroId;
    }

    public void setLivroId(Integer livroId) {
        this.livroId = livroId;
    }
}
