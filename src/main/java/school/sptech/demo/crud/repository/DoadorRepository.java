package school.sptech.demo.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.demo.crud.entity.Doador;

import java.util.Optional;

public interface DoadorRepository extends JpaRepository<Doador, Integer> {
Optional<Doador> findByCpfAndIdNot(String cpf, Integer id);
}


