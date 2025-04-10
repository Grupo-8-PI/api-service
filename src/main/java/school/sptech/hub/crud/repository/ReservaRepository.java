package school.sptech.hub.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.hub.crud.entity.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
}
