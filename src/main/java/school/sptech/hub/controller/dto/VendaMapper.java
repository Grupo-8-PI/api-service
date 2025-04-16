package school.sptech.hub.controller.dto;

import school.sptech.hub.entity.Usuario;
import school.sptech.hub.entity.Venda;

public class VendaMapper {

    public static Venda toEntity(VendaCreateDto dto){
        Venda venda = new Venda();
        venda.setDtLimite(dto.getDtLimite());
        venda.setDtReserva(dto.getDtReserva());
        venda.setStatusReserva(dto.getStatusReserva());
        venda.setTotalReserva(dto.getTotalReserva());
        return venda;
    }
}
