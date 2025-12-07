package school.sptech.hub.infraestructure.gateways.livro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @CacheEvict(value = "livros", allEntries = true)
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

        if (entity.isEmpty()) {
            return Optional.empty();
        }

        try {
            Livro livro = LivroEntityMapper.toDomain(entity.get());
            return Optional.of(livro);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao mapear LivroEntity para Livro (ID: " + id + "): " + e.getMessage(), e);
        }
    }

    @Override
    public List<Livro> findAll() {
        List<LivroEntity> entities = livroRepository.findAll();
        return entities.stream()
                .map(LivroEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "livros", allEntries = true)
    public Optional<Livro> updateLivro(Livro livro) {
        LivroEntity livroEntity = LivroEntityMapper.toEntity(livro);
        LivroEntity updatedEntity = livroRepository.save(livroEntity);

        livroRepository.flush();
        entityManager.clear();

        LivroEntity reloadedEntity = livroRepository.findById(updatedEntity.getId()).orElse(updatedEntity);

        return Optional.of(LivroEntityMapper.toDomain(reloadedEntity));
    }

    @Override
    @CacheEvict(value = "livros", allEntries = true)
    public void deleteLivro(Integer id) {
        livroRepository.deleteById(id);
    }

    @Override
    public Optional<Livro> findByIsbn(String isbn) {
        Optional<LivroEntity> entity = livroRepository.findByIsbn(isbn);
        return entity.map(LivroEntityMapper::toDomain);
    }

    @Override
    public List<Livro> findByAcabamentoId(Integer acabamentoId) {
        List<LivroEntity> entities = livroRepository.findByAcabamento_Id(acabamentoId);
        return entities.stream()
                .map(LivroEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Livro> findByConservacaoId(Integer conservacaoId) {
        List<LivroEntity> entities = livroRepository.findByEstadoConservacao_Id(conservacaoId);
        return entities.stream()
                .map(LivroEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Livro> findByCategoriaId(Integer categoriaId) {
        List<LivroEntity> entities = livroRepository.findByCategoria_Id(categoriaId);
        return entities.stream()
                .map(LivroEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllDistinctCategorias() {
        return livroRepository.findAllDistinctCategorias();
    }

    @Override
    public List<Livro> findRecommendedRandomLivros() {
        List<LivroEntity> entities = livroRepository.findRecommendedRandomLivros();
        return entities.stream()
                .map(LivroEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Livro> findRandomLivro() {
        Optional<LivroEntity> entity = livroRepository.findRandomLivro();
        return entity.map(LivroEntityMapper::toDomain);
    }

    @Override
    public List<Livro> findTop3ByOrderByDataAdicaoDesc() {
        List<LivroEntity> entities = livroRepository.findTop3ByOrderByDataAdicaoDesc();
        return entities.stream()
                .map(LivroEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Livro> findAllPaginated(int page, int size) {
        Page<LivroEntity> entityPage = livroRepository.findAll(PageRequest.of(page, size));
        return entityPage.map(LivroEntityMapper::toDomain);
    }
}
