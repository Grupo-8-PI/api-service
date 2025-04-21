package school.sptech.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.hub.controller.dto.LivroCreateDto;
import school.sptech.hub.controller.dto.LivroMapper;
import school.sptech.hub.controller.dto.LivroResponseDto;
import school.sptech.hub.entity.Livro;
import school.sptech.hub.repository.AcabamentoRepository;
import school.sptech.hub.repository.CategoriaRepository;
import school.sptech.hub.repository.ConservacaoRepository;
import school.sptech.hub.repository.LivroRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private AcabamentoRepository acabamentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ConservacaoRepository conservacaoRepository;


    public Livro createNewLivro(LivroCreateDto livro) {
        Livro livroEntity = LivroMapper.toEntity(livro);
        if(livroEntity == null) {
            return null;
        }

        Livro livroPostado = repository.save(livroEntity);
        return livroPostado;

    }

    public List<LivroResponseDto> listarLivros() {
        List<Livro> livros = repository.findAll();
        List<LivroResponseDto> livrosResponse = new ArrayList<>();
        for (int i = 0; i < livros.size(); i++) {
            Livro livroActual = livros.get(i);
            LivroResponseDto livroResponseDto = LivroMapper.toResponseDto(livroActual);
            livrosResponse.add(livroResponseDto);
        }
        return livrosResponse;
    }


    public LivroResponseDto buscarLivroPorId(Integer id) {
        Livro livro = repository.findById(id).orElse(null);
        if(livro == null) {
            return null;
        }
       return LivroMapper.toResponseDto(livro);
    }

    public Livro atualizarLivro(Integer id, Livro livro) {
        Livro existingLivro = repository.findById(id).orElse(null);
        if(existingLivro == null){
            return null;
        }
        Livro updatedLivro = LivroMapper.updateLivroFields(existingLivro, livro);
        return repository.save(updatedLivro);
    }

    public Livro deletarLivro(Integer id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
            return repository.findById(id).orElse(null);
        }
            return null;
    }

}
