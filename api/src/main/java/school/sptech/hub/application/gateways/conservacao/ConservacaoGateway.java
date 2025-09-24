package school.sptech.hub.application.gateways.conservacao;

import school.sptech.hub.domain.entity.Conservacao;

import java.util.List;
import java.util.Optional;

public interface ConservacaoGateway {
    Optional<Conservacao> createConservacao(Conservacao conservacao);
    Optional<Conservacao> findById(Integer id);
    List<Conservacao> findAll();
    Optional<Conservacao> updateConservacao(Conservacao conservacao);
    void deleteConservacao(Integer id);
    Optional<Conservacao> findByEstadoConservacao(String estadoConservacao);
}
