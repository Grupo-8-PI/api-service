package school.sptech.hub.domain.dto.venda;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta com os dados públicos da reserva/venda")
public class VendaResponseDto {

    @Schema(description = "ID único da venda/reserva no sistema", example = "1")
    private int id;

    @Schema(description = "Data da reserva/venda no formato YYYY-MM-DD", example = "2025-05-22")
    private String dtReserva;

    @Schema(description = "Data limite da busca do livro no formato YYYY-MM-DD", example = "2025-05-24")
    private String dtLimite;

    @Schema(description = "Status de aprovação da reserva", example = "Ok!")
    private String statusReserva;

    @Schema(description = "Valor total da reserva/venda dos livros", example = "25")
    private Integer totalReserva;

    @Schema(description = "ID do livro associado à venda/reserva", example = "10")
    private Integer livroId;

    @Schema(description = "ID do usuário que realizou a reserva", example = "5")
    private Integer usuarioId;


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

    public Integer getLivroId() {
        return livroId;
    }

    public void setLivroId(Integer livroId) {
        this.livroId = livroId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
