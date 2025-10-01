package school.sptech.hub.application.gateways.livro;

import school.sptech.hub.domain.entity.Livro;

import java.util.List;
import java.util.Optional;

public interface LivroGateway {
    Optional<Livro> createLivro(Livro livro);
    Optional<Livro> findById(Integer id);
    List<Livro> findAll();
    Optional<Livro> updateLivro(Livro livro);
    void deleteLivro(Integer id);
    Optional<Livro> findByIsbn(String isbn);
    List<Livro> findByAcabamentoId(Integer acabamentoId);
    List<Livro> findByConservacaoId(Integer conservacaoId);
    List<Livro> findByCategoriaId(Integer categoriaId);
}
