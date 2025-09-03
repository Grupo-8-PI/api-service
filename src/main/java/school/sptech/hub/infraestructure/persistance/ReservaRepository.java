package school.sptech.hub.infraestructure.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.hub.domain.entity.Venda;

public interface ReservaRepository extends JpaRepository<Venda, Integer> {
}
