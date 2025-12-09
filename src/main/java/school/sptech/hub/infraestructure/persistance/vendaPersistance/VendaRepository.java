package school.sptech.hub.infraestructure.persistance.vendaPersistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface VendaRepository extends JpaRepository<VendaEntity, Integer> {
    List<VendaEntity> findByUsuariosId(Integer clienteId);
    boolean existsByLivroId(Integer livroId);

    @Query("SELECT DISTINCT v.livro.id FROM VendaEntity v WHERE v.livro.id IN :livroIds")
    Set<Integer> findDistinctLivroIdsByLivroIdIn(@Param("livroIds") Set<Integer> livroIds);
}
