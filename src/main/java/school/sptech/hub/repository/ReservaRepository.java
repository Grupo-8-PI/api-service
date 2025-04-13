package school.sptech.hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.hub.entity.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
}
