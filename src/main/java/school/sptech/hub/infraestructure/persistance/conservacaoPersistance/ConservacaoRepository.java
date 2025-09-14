package school.sptech.hub.infraestructure.persistance.conservacaoPersistance;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.hub.domain.entity.TipoConservacao;

import java.util.Optional;

public interface ConservacaoRepository extends JpaRepository<ConservacaoEntity, Integer> {
    Optional<ConservacaoEntity> findByTipoConservacao(TipoConservacao tipoConservacao);
}
