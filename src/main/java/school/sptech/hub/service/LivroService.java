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
        livro.setAcabamento(acabamentoRepository.findById(livro.getAcabamento().getId()).orElse(null));
        livro.setCategoria(categoriaRepository.findById(livro.getCategoria().getId()).orElse(null));
        livro.setEstadoConservacao(conservacaoRepository.findById(livro.getEstadoConservacao().getId()).orElse(null));
        Livro livroCreated = livroRepository.save(livro);
        if(livroCreated == null) {
            return null;
        }

        return livroCreated;

    }

}
