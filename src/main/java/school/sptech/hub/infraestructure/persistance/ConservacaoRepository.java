package school.sptech.hub.infraestructure.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.hub.domain.entity.Conservacao;

public interface ConservacaoRepository extends JpaRepository<Conservacao, Integer> {
}
