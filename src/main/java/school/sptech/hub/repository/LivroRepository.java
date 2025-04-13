package school.sptech.hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.hub.entity.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
}
