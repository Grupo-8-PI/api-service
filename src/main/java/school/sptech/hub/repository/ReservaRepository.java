package school.sptech.hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.hub.entity.Venda;

public interface ReservaRepository extends JpaRepository<Venda, Integer> {
}
