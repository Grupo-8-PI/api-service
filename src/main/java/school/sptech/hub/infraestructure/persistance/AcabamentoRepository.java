package school.sptech.hub.infraestructure.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.hub.domain.entity.Acabamento;

public interface AcabamentoRepository extends JpaRepository<Acabamento, Integer> {
}
