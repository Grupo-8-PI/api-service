package school.sptech.hub.infraestructure.gateways.venda;

import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.infraestructure.persistance.vendaPersistance.VendaEntity;

public class VendaEntityMapper {

    public VendaEntity toEntity(Venda venda) {
        if (venda == null) {
            return null;
        }

        VendaEntity vendaEntity = new VendaEntity();
        vendaEntity.setId(venda.getId());
        vendaEntity.setDataVenda(venda.getDataVenda());
        vendaEntity.setQuantidade(venda.getQuantidade());
        vendaEntity.setPrecoTotal(venda.getPrecoTotal());
        vendaEntity.setUsuarios(venda.getUsuarios());
        vendaEntity.setLivros(venda.getLivros());

        return vendaEntity;
    }

    public Venda toDomain(VendaEntity vendaEntity) {
        if (vendaEntity == null) {
            return null;
        }

        Venda venda = new Venda();
        venda.setId(vendaEntity.getId());
        venda.setDataVenda(vendaEntity.getDataVenda());
        venda.setQuantidade(vendaEntity.getQuantidade());
        venda.setPrecoTotal(vendaEntity.getPrecoTotal());
        venda.setUsuarios(vendaEntity.getUsuarios());
        venda.setLivros(vendaEntity.getLivros());

        return venda;
    }
}
