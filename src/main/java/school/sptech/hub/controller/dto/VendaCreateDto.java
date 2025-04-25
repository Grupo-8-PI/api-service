package school.sptech.hub.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Schema(description = "DTO utilizado para a criação de uma nova venda ou reserva")
public class VendaCreateDto {

    @Schema(description = "Data e hora da reserva", example = "2025-05-01T14:30:00")
    @NotNull(message = "A data da reserva é obrigatória")
    @FutureOrPresent(message = "A data da reserva deve ser no presente ou futuro")
    private LocalDateTime dtReserva;

    @Schema(description = "Data e hora limite para a reserva", example = "2025-05-03T18:00:00")
    @NotNull(message = "A data limite é obrigatória")
    @FutureOrPresent(message = "A data limite deve ser no presente ou futuro")
    private LocalDateTime dtLimite;

    @Schema(description = "Status da reserva", example = "Confirmada")
    @Size(max = 45, message = "O status da reserva deve ter no máximo 45 caracteres")
    private String statusReserva;

    @Schema(description = "Valor total da reserva", example = "150")
    @NotNull(message = "O total da reserva é obrigatório")
    private Integer totalReserva;


    public LocalDateTime getDtReserva() {
        return dtReserva;
    }

    public void setDtReserva(LocalDateTime dtReserva) {
        this.dtReserva = dtReserva;
    }

    public LocalDateTime getDtLimite() {
        return dtLimite;
    }

    public void setDtLimite(LocalDateTime dtLimite) {
        this.dtLimite = dtLimite;
    }

    public String getStatusReserva() {
        return statusReserva;
    }

    public void setStatusReserva(String statusReserva) {
        this.statusReserva = statusReserva;
    }

    public Integer getTotalReserva() {
        return totalReserva;
    }

    public void setTotalReserva(Integer totalReserva) {
        this.totalReserva = totalReserva;
    }
}
