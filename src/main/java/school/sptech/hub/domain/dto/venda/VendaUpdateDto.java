package school.sptech.hub.domain.dto.venda;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para atualização de uma venda/reserva existente")
public class VendaUpdateDto {

    @Schema(description = "Status da reserva", example = "CONCLUIDA", allowableValues = {"pendente", "confirmada", "cancelada", "finalizada", "CONCLUIDA"})
    @Size(max = 45, message = "O status da reserva deve ter no máximo 45 caracteres")
    private String statusReserva;

    public String getStatusReserva() {
        return statusReserva;
    }

    public void setStatusReserva(String statusReserva) {
        this.statusReserva = statusReserva;
    }
}

