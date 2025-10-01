package school.sptech.hub.infraestructure.gateways.livro;

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

    public LivroRepositoryGateway(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @Override
    public Optional<Livro> createLivro(Livro livro) {
        LivroEntity livroEntity = LivroEntityMapper.toEntity(livro);
        LivroEntity savedEntity = livroRepository.save(livroEntity);
        return Optional.of(LivroEntityMapper.toDomain(savedEntity));
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
        return Optional.of(LivroEntityMapper.toDomain(updatedEntity));
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
}
