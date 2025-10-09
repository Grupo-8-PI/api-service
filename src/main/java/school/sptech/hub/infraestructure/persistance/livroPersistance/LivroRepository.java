package school.sptech.hub.infraestructure.persistance.livroPersistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LivroRepository extends JpaRepository<LivroEntity, Integer> {
    Optional<LivroEntity> findByIsbn(String isbn);

    @Query("SELECT l FROM LivroEntity l LEFT JOIN FETCH l.categoria LEFT JOIN FETCH l.estadoConservacao LEFT JOIN FETCH l.acabamento")
    Page<LivroEntity> findAllWithAssociations(Pageable pageable);
}
