package school.sptech.hub.infraestructure.persistance.livroPersistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<LivroEntity, Integer> {
    Optional<LivroEntity> findByIsbn(String isbn);

    @Query("SELECT DISTINCT l.categoria.nomeCategoria FROM LivroEntity l ORDER BY l.categoria.nomeCategoria")
    List<String> findAllDistinctCategorias();

    @Query(value = "SELECT * FROM livro ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<LivroEntity> findRecommendedRandomLivros();

    @Query(value = "SELECT * FROM livro ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<LivroEntity> findRandomLivro();
}

