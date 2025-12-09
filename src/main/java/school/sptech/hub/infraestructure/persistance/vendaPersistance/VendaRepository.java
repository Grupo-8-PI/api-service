package school.sptech.hub.infraestructure.persistance.vendaPersistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface VendaRepository extends JpaRepository<VendaEntity, Integer> {
    List<VendaEntity> findByUsuariosId(Integer clienteId);

    @Query("SELECT COUNT(v) > 0 FROM VendaEntity v " +
           "WHERE v.livro.id = :livroId " +
           "AND v.statusReserva IN ('CONFIRMADA', 'CONCLUIDA')")
    boolean existsByLivroId(@Param("livroId") Integer livroId);

    @Query("SELECT DISTINCT v.livro.id FROM VendaEntity v " +
           "WHERE v.livro.id IN :livroIds " +
           "AND v.statusReserva IN ('CONFIRMADA', 'CONCLUIDA')")
    Set<Integer> findDistinctLivroIdsByLivroIdIn(@Param("livroIds") Set<Integer> livroIds);
}
