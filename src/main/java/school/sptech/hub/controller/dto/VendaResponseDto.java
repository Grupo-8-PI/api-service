package school.sptech.hub.controller.dto;

public class VendaResponseDto {
    private int id;
    private String dtReserva;
    private String dtLimite;
    private String statusReserva;
    private Integer totalReserva;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDtReserva() {
        return dtReserva;
    }

    public void setDtReserva(String dtReserva) {
        this.dtReserva = dtReserva;
    }

    public String getDtLimite() {
        return dtLimite;
    }

    public void setDtLimite(String dtLimite) {
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
