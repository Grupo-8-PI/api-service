package school.sptech.hub.application.gateways.acabamento;

import school.sptech.hub.domain.entity.Acabamento;

import java.util.List;
import java.util.Optional;

public interface AcabamentoGateway {
    Optional<Acabamento> createAcabamento(Acabamento acabamento);
    Optional<Acabamento> findById(Integer id);
    List<Acabamento> findAll();
    Optional<Acabamento> updateAcabamento(Acabamento acabamento);
    void deleteAcabamento(Integer id);
    Optional<Acabamento> findByTipoAcabamento(String tipoAcabamento);
}
