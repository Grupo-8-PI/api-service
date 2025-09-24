package school.sptech.hub.infraestructure.gateways.conservacao;

import school.sptech.hub.domain.entity.Conservacao;
import school.sptech.hub.infraestructure.persistance.conservacaoPersistance.ConservacaoEntity;

public class ConservacaoEntityMapper {

    public static ConservacaoEntity toEntity(Conservacao conservacao) {
        if (conservacao == null) return null;

        ConservacaoEntity entity = new ConservacaoEntity();
        entity.setId(conservacao.getId());
        entity.setEstadoConservacao(conservacao.getEstadoConservacao());

        return entity;
    }

    public static Conservacao toDomain(ConservacaoEntity entity) {
        if (entity == null) return null;

        Conservacao conservacao = new Conservacao();
        conservacao.setId(entity.getId());
        conservacao.setEstadoConservacao(entity.getEstadoConservacao());

        return conservacao;
    }
}
