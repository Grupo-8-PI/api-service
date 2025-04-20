package school.sptech.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.hub.entity.Livro;
import school.sptech.hub.repository.AcabamentoRepository;
import school.sptech.hub.repository.CategoriaRepository;
import school.sptech.hub.repository.ConservacaoRepository;
import school.sptech.hub.repository.LivroRepository;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AcabamentoRepository acabamentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ConservacaoRepository conservacaoRepository;


    public Livro createNewLivro(Livro livro) {
        livro.setId(null);
        if (livro.getAcabamento() != null && livro.getAcabamento().getId() == null) {
            livro.setAcabamento(acabamentoRepository.save(livro.getAcabamento()));
        }

        if (livro.getCategoria() != null && livro.getCategoria().getId() == null) {
            livro.setCategoria(categoriaRepository.save(livro.getCategoria()));
        }

        if (livro.getEstadoConservacao() != null && livro.getEstadoConservacao().getId() == null) {
            livro.setEstadoConservacao(conservacaoRepository.save(livro.getEstadoConservacao()));
        }
        Livro livroCreated = livroRepository.save(livro);
        if(livroCreated == null) {
            return null;
        }

        return livroCreated;

    }

}
