package school.sptech.hub.infraestructure.gateways.acabamento;

import school.sptech.hub.domain.entity.Acabamento;
import school.sptech.hub.infraestructure.persistance.acabamentoPersistance.AcabamentoEntity;

public class AcabamentoEntityMapper {

    public static AcabamentoEntity toEntity(Acabamento acabamento) {
        if (acabamento == null) return null;

        AcabamentoEntity entity = new AcabamentoEntity();
        entity.setId(acabamento.getId());
        entity.setTipoAcabamento(acabamento.getTipoAcabamento());

        return entity;
    }

    public static Acabamento toDomain(AcabamentoEntity entity) {
        if (entity == null) return null;

        Acabamento acabamento = new Acabamento();
        acabamento.setId(entity.getId());
        acabamento.setTipoAcabamento(entity.getTipoAcabamento());

        return acabamento;
    }
}
