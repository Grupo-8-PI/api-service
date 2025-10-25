package school.sptech.hub.infraestructure.persistance.vendaPersistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaRepository extends JpaRepository<VendaEntity, Integer> {
    List<VendaEntity> findByUsuariosId(Integer clienteId);
}
