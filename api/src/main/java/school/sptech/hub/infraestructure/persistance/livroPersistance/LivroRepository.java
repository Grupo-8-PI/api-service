package school.sptech.hub.infraestructure.persistance.livroPersistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LivroRepository extends JpaRepository<LivroEntity, Integer> {
    Optional<LivroEntity> findByIsbn(String isbn);
}
