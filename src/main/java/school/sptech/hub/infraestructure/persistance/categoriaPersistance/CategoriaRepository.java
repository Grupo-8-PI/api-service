package school.sptech.hub.infraestructure.persistance.categoriaPersistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Integer> {
    Optional<CategoriaEntity> findByNomeCategoria(String nomeCategoria);
}
