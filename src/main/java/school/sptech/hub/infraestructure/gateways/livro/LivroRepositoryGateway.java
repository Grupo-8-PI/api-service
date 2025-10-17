package school.sptech.hub.infraestructure.gateways.livro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.infraestructure.persistance.livroPersistance.LivroEntity;
import school.sptech.hub.infraestructure.persistance.livroPersistance.LivroRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LivroRepositoryGateway implements LivroGateway {

    private final LivroRepository livroRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public LivroRepositoryGateway(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @Override
    public Optional<Livro> createLivro(Livro livro) {
        LivroEntity livroEntity = LivroEntityMapper.toEntity(livro);
        LivroEntity savedEntity = livroRepository.save(livroEntity);
        livroRepository.flush();
        entityManager.clear();
        LivroEntity reloadedEntity = livroRepository.findById(savedEntity.getId()).orElse(savedEntity);

        return Optional.of(LivroEntityMapper.toDomain(reloadedEntity));
    }

    @Override
    public Optional<Livro> findById(Integer id) {
        Optional<LivroEntity> entity = livroRepository.findById(id);
        return entity.map(LivroEntityMapper::toDomain);
    }

    @Override
    public List<Livro> findAll() {
        List<LivroEntity> entities = livroRepository.findAll();
        return entities.stream()
                .map(LivroEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Livro> updateLivro(Livro livro) {
        LivroEntity livroEntity = LivroEntityMapper.toEntity(livro);
        LivroEntity updatedEntity = livroRepository.save(livroEntity);

        livroRepository.flush();
        entityManager.clear();

        LivroEntity reloadedEntity = livroRepository.findById(updatedEntity.getId()).orElse(updatedEntity);

        return Optional.of(LivroEntityMapper.toDomain(reloadedEntity));
    }

    @Override
    public void deleteLivro(Integer id) {
        livroRepository.deleteById(id);
    }

    @Override
    public Optional<Livro> findByIsbn(String isbn) {
        Optional<LivroEntity> entity = livroRepository.findByIsbn(isbn);
        return entity.map(LivroEntityMapper::toDomain);
    }
}
