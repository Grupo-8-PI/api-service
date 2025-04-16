package school.sptech.hub.controller.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class VendaCreateDto {

    @NotBlank
    private LocalDateTime dtReserva;

    @NotBlank
    private LocalDateTime dtLimite;

    @Size(max=45)
    private String statusReserva;

    public Integer getTotalReserva() {
        return totalReserva;
    }

    public void setTotalReserva(Integer totalReserva) {
        this.totalReserva = totalReserva;
    }

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
}
