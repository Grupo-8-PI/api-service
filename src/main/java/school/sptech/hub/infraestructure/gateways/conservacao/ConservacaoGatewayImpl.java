package school.sptech.hub.infraestructure.gateways.conservacao;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.conservacao.ConservacaoGateway;
import school.sptech.hub.domain.entity.Conservacao;
import school.sptech.hub.infraestructure.persistance.conservacaoPersistance.ConservacaoEntity;
import school.sptech.hub.infraestructure.persistance.conservacaoPersistance.ConservacaoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ConservacaoGatewayImpl implements ConservacaoGateway {

    private final ConservacaoRepository conservacaoRepository;

    public ConservacaoGatewayImpl(ConservacaoRepository conservacaoRepository) {
        this.conservacaoRepository = conservacaoRepository;
    }

    @Override
    public Optional<Conservacao> findById(Integer id) {
        return conservacaoRepository.findById(id)
                .map(ConservacaoEntityMapper::toDomain);
    }

    @Override
    public List<Conservacao> findAll() {
        return conservacaoRepository.findAll().stream()
                .map(ConservacaoEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Conservacao> createConservacao(Conservacao conservacao) {
        ConservacaoEntity entity = ConservacaoEntityMapper.toEntity(conservacao);
        ConservacaoEntity savedEntity = conservacaoRepository.save(entity);
        return Optional.of(ConservacaoEntityMapper.toDomain(savedEntity));
    }

    @Override
    public Optional<Conservacao> updateConservacao(Conservacao conservacao) {
        if (conservacao.getId() == null || !conservacaoRepository.existsById(conservacao.getId())) {
            return Optional.empty();
        }
        ConservacaoEntity entity = ConservacaoEntityMapper.toEntity(conservacao);
        ConservacaoEntity savedEntity = conservacaoRepository.save(entity);
        return Optional.of(ConservacaoEntityMapper.toDomain(savedEntity));
    }

    @Override
    public void deleteConservacao(Integer id) {
        conservacaoRepository.deleteById(id);
    }

    @Override
    public Optional<Conservacao> findByEstadoConservacao(String estadoConservacao) {
        return conservacaoRepository.findByEstadoConservacao(estadoConservacao)
                .map(ConservacaoEntityMapper::toDomain);
    }
}
