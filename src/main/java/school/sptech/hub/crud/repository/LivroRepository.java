package school.sptech.hub.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.hub.crud.entity.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
}
