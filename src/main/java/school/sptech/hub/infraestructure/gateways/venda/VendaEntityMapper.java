package school.sptech.hub.infraestructure.gateways.venda;

import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.infraestructure.persistance.vendaPersistance.VendaEntity;
import school.sptech.hub.infraestructure.gateways.usuario.UsuarioEntityMapper;

public class VendaEntityMapper {

    public static VendaEntity toEntity(Venda venda) {
        if (venda == null) return null;

        VendaEntity vendaEntity = new VendaEntity();
        vendaEntity.setId(venda.getId());
        vendaEntity.setDtReserva(venda.getDtReserva());
        vendaEntity.setDtLimite(venda.getDtLimite());
        vendaEntity.setStatusReserva(venda.getStatusReserva());
        vendaEntity.setTotalReserva(venda.getTotalReserva());
        vendaEntity.setUsuarios(UsuarioEntityMapper.toEntity(venda.getUsuarios()));

        return vendaEntity;
    }

    public static Venda toDomain(VendaEntity vendaEntity) {
        if (vendaEntity == null) return null;

        Venda venda = new Venda();
        venda.setId(vendaEntity.getId());
        venda.setDtReserva(vendaEntity.getDtReserva());
        venda.setDtLimite(vendaEntity.getDtLimite());
        venda.setStatusReserva(vendaEntity.getStatusReserva());
        venda.setTotalReserva(vendaEntity.getTotalReserva());
        venda.setUsuarios(UsuarioEntityMapper.toDomain(vendaEntity.getUsuarios()));

        return venda;
    }
}
