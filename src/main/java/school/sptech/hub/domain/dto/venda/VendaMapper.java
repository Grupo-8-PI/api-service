package school.sptech.hub.domain.dto.venda;

import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.infraestructure.persistance.vendaPersistance.VendaEntity;

public class VendaMapper {

    public static Venda toEntity(VendaCreateDto dto){
        Venda venda = new Venda();
        venda.setDtLimite(dto.getDtLimite());
        venda.setDtReserva(dto.getDtReserva());
        venda.setStatusReserva(dto.getStatusReserva());
        venda.setTotalReserva(dto.getTotalReserva());
        // Nota: O usuário deve ser associado no UseCase com base no contexto de autenticação
        return venda;
    }

    public static Venda toEntity(VendaCreateDto dto, Usuario usuario){
        Venda venda = toEntity(dto);
        venda.setUsuarios(usuario);
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
    public static Venda toDomain(VendaEntity entity) {
        Venda venda = new Venda();
        venda.setId(entity.getId());
        venda.setDtReserva(entity.getDtReserva());
        venda.setDtLimite(entity.getDtLimite());
        venda.setStatusReserva(entity.getStatusReserva());
        venda.setTotalReserva(entity.getTotalReserva());
        // Adicione outros campos conforme necessário
        return venda;
    }
}
