package school.sptech.hub.infraestructure.gateways.venda;

import school.sptech.hub.application.gateways.venda.VendaGateway;
import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.infraestructure.persistance.vendaPersistance.VendaEntity;
import school.sptech.hub.infraestructure.persistance.vendaPersistance.VendaRepository;

import java.util.Optional;

public class VendaRepositoryGateway implements VendaGateway {
    private final VendaRepository vendaRepository;
    private final VendaEntityMapper vendaMapper;

    public VendaRepositoryGateway(VendaRepository vendaRepository, VendaEntityMapper vendaMapper) {
        this.vendaRepository = vendaRepository;
        this.vendaMapper = vendaMapper;
    }

    @Override
    public Optional<Venda> createVenda(Venda venda) {
        VendaEntity vendaEntity = vendaMapper.toEntity(venda);
        VendaEntity savedEntity = vendaRepository.save(vendaEntity);
        return Optional.of(vendaMapper.toDomain(savedEntity));
    }

    @Override
    public Optional<Venda> findById(Integer id) {
        Optional<VendaEntity> vendaEntity = vendaRepository.findById(id);
        return vendaEntity.map(vendaMapper::toDomain);
    }

    @Override
    public Optional<Venda> updateVenda(Venda venda) {
        VendaEntity vendaEntity = vendaMapper.toEntity(venda);
        VendaEntity savedEntity = vendaRepository.save(vendaEntity);
        return Optional.of(vendaMapper.toDomain(savedEntity));
    }

    @Override
    public void deleteVenda(Venda venda) {
        VendaEntity vendaEntity = vendaMapper.toEntity(venda);
        vendaRepository.delete(vendaEntity);
    }

    @Override
    public boolean reservaPertenceAoUsuario(Integer idReserva, String emailUsuario) {
        Optional<VendaEntity> vendaEntity = vendaRepository.findById(idReserva);
        return vendaEntity.isPresent() && vendaEntity.get().getUsuarios().getEmail().equals(emailUsuario);
    }
}
