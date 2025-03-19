package school.sptech.demo.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.demo.crud.entity.Doador;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoadorRepository extends JpaRepository<Doador, Integer> {
Optional<Doador> findByCpfAndIdNot(String cpf, Integer id);
List<Doador> findByNome(String nome);
}


