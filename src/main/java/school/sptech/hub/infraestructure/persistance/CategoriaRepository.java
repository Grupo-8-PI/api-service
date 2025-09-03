package school.sptech.hub.infraestructure.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.hub.domain.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
