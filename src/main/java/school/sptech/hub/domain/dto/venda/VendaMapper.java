package school.sptech.hub.domain.dto.venda;

import school.sptech.hub.domain.entity.Venda;

public class VendaMapper {

    public static Venda toEntity(VendaCreateDto dto){
        Venda venda = new Venda();
        venda.setDtLimite(dto.getDtLimite());
        venda.setDtReserva(dto.getDtReserva());
        venda.setStatusReserva(dto.getStatusReserva());
        venda.setTotalReserva(dto.getTotalReserva());
        return venda;
    }

    public static VendaResponseDto toResponseDto(Venda venda){
        VendaResponseDto dto = new VendaResponseDto();
        dto.setId(venda.getId());
        dto.setDtLimite(venda.getDtLimite().toString());
        dto.setDtReserva(venda.getDtReserva().toString());
        dto.setStatusReserva(venda.getStatusReserva());
        dto.setTotalReserva(venda.getTotalReserva());
        return dto;
    }
    public static Venda updateVendaFields(Venda existingVenda, Venda toBeUpdatedVenda) {
        if (toBeUpdatedVenda.getDtLimite() != null) {
            existingVenda.setDtLimite(toBeUpdatedVenda.getDtLimite());
        }
        if (toBeUpdatedVenda.getDtReserva() != null) {
            existingVenda.setDtReserva(toBeUpdatedVenda.getDtReserva());
        }
        if (toBeUpdatedVenda.getStatusReserva() != null) {
            existingVenda.setStatusReserva(toBeUpdatedVenda.getStatusReserva());
        }
        if (toBeUpdatedVenda.getTotalReserva() != null) {
            existingVenda.setTotalReserva(toBeUpdatedVenda.getTotalReserva());
        }
        return existingVenda;
    }
}
