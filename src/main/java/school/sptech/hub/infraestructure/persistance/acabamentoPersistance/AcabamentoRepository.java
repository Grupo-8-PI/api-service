package school.sptech.hub.infraestructure.persistance.acabamentoPersistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcabamentoRepository extends JpaRepository<AcabamentoEntity, Integer> {
    Optional<AcabamentoEntity> findByTipoAcabamento(String tipoAcabamento);
}
