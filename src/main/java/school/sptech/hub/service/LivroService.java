package school.sptech.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.hub.entity.Livro;
import school.sptech.hub.repository.AcabamentoRepository;
import school.sptech.hub.repository.CategoriaRepository;
import school.sptech.hub.repository.ConservacaoRepository;
import school.sptech.hub.repository.LivroRepository;

import java.util.List;

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

    public List<Livro> listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        if(livros == null) {
            return null;
        }
        return livros;
    }

    public Livro buscarLivroPorId(Integer id) {
        Livro livro = livroRepository.findById(id).orElse(null);
        if(livro == null) {
            return null;
        }
        return livro;
    }

    public Livro atualizarLivro(Integer id, Livro livro) {
        livro.setId(id);
        Livro livroAtualizado = livroRepository.save(livro);
        if(livroAtualizado == null) {
            return null;
        }
        return livroAtualizado;
    }

    public Livro deletarLivro(Integer id) {
        if(livroRepository.existsById(id)) {
            livroRepository.deleteById(id);
            return livroRepository.findById(id).orElse(null);
        }
            return null;
    }

}
