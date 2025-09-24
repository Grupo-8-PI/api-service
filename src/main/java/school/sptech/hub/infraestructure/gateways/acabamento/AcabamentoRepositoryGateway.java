package school.sptech.hub.infraestructure.gateways.acabamento;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.acabamento.AcabamentoGateway;
import school.sptech.hub.domain.entity.Acabamento;
import school.sptech.hub.domain.entity.TipoAcabamento;
import school.sptech.hub.infraestructure.persistance.acabamentoPersistance.AcabamentoEntity;
import school.sptech.hub.infraestructure.persistance.acabamentoPersistance.AcabamentoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AcabamentoRepositoryGateway implements AcabamentoGateway {

    private final AcabamentoRepository acabamentoRepository;

    public AcabamentoRepositoryGateway(AcabamentoRepository acabamentoRepository) {
        this.acabamentoRepository = acabamentoRepository;
    }

    @Override
    public Optional<Acabamento> createAcabamento(Acabamento acabamento) {
        AcabamentoEntity entity = AcabamentoEntityMapper.toEntity(acabamento);
        AcabamentoEntity savedEntity = acabamentoRepository.save(entity);
        return Optional.of(AcabamentoEntityMapper.toDomain(savedEntity));
    }

    @Override
    public Optional<Acabamento> findById(Integer id) {
        Optional<AcabamentoEntity> entity = acabamentoRepository.findById(id);
        return entity.map(AcabamentoEntityMapper::toDomain);
    }

    @Override
    public List<Acabamento> findAll() {
        List<AcabamentoEntity> entities = acabamentoRepository.findAll();
        return entities.stream()
                .map(AcabamentoEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Acabamento> updateAcabamento(Acabamento acabamento) {
        AcabamentoEntity entity = AcabamentoEntityMapper.toEntity(acabamento);
        AcabamentoEntity updatedEntity = acabamentoRepository.save(entity);
        return Optional.of(AcabamentoEntityMapper.toDomain(updatedEntity));
    }

    @Override
    public void deleteAcabamento(Integer id) {
        acabamentoRepository.deleteById(id);
    }

    @Override
    public Optional<Acabamento> findByTipoAcabamento(TipoAcabamento tipoAcabamento) {
        Optional<AcabamentoEntity> entity = acabamentoRepository.findByTipoAcabamento(tipoAcabamento);
        return entity.map(AcabamentoEntityMapper::toDomain);
    }
}
