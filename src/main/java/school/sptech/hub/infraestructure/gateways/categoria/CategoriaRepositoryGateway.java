package school.sptech.hub.infraestructure.gateways.categoria;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.categoria.CategoriaGateway;
import school.sptech.hub.domain.entity.Categoria;
import school.sptech.hub.infraestructure.persistance.categoriaPersistance.CategoriaEntity;
import school.sptech.hub.infraestructure.persistance.categoriaPersistance.CategoriaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CategoriaRepositoryGateway implements CategoriaGateway {

    private final CategoriaRepository categoriaRepository;

    public CategoriaRepositoryGateway(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Optional<Categoria> createCategoria(Categoria categoria) {
        CategoriaEntity entity = CategoriaEntityMapper.toEntity(categoria);
        CategoriaEntity savedEntity = categoriaRepository.save(entity);
        return Optional.of(CategoriaEntityMapper.toDomain(savedEntity));
    }

    @Override
    public Optional<Categoria> findById(Integer id) {
        Optional<CategoriaEntity> entity = categoriaRepository.findById(id);
        return entity.map(CategoriaEntityMapper::toDomain);
    }

    @Override
    public List<Categoria> findAll() {
        List<CategoriaEntity> entities = categoriaRepository.findAll();
        return entities.stream()
                .map(CategoriaEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Categoria> updateCategoria(Categoria categoria) {
        CategoriaEntity entity = CategoriaEntityMapper.toEntity(categoria);
        CategoriaEntity updatedEntity = categoriaRepository.save(entity);
        return Optional.of(CategoriaEntityMapper.toDomain(updatedEntity));
    }

    @Override
    public void deleteCategoria(Integer id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public Optional<Categoria> findByNome(String nome) {
        Optional<CategoriaEntity> entity = categoriaRepository.findByNomeCategoria(nome);
        return entity.map(CategoriaEntityMapper::toDomain);
    }
}
