package school.sptech.hub.infraestructure.gateways.venda;

import school.sptech.hub.domain.entity.Venda;
import school.sptech.hub.infraestructure.persistance.vendaPersistance.VendaRepository;
import school.sptech.hub.infraestructure.persistance.vendaPersistance.VendaEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VendaRepositoryGateway {
    public static Venda save(Venda venda, VendaRepository vendaRepository) {
        VendaEntity entity = VendaEntityMapper.toEntity(venda);
        VendaEntity saved = vendaRepository.save(entity);
        return VendaEntityMapper.toDomain(saved);
    }

    public static Optional<Venda> findById(Integer id, VendaRepository vendaRepository) {
        return vendaRepository.findById(id)
                .map(VendaEntityMapper::toDomain);
    }

    public static List<Venda> findAll(VendaRepository vendaRepository) {
        return vendaRepository.findAll().stream()
                .map(VendaEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    public static void delete(Venda venda, VendaRepository vendaRepository) {
        VendaEntity entity = VendaEntityMapper.toEntity(venda);
        vendaRepository.delete(entity);
    }
}
