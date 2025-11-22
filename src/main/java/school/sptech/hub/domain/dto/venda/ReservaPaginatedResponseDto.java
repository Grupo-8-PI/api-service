package school.sptech.hub.domain.dto.venda;

import java.util.List;

public class ReservaPaginatedResponseDto {
    private List<VendaResponseDto> reservas;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public ReservaPaginatedResponseDto(List<VendaResponseDto> reservas, int page, int size, long totalElements, int totalPages) {
        this.reservas = reservas;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<VendaResponseDto> getReservas() {
        return reservas;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }
}

