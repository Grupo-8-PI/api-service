package school.sptech.hub.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.hub.application.adapter.ChatGptAdapter;
import school.sptech.hub.domain.dto.livro.LivroComSinopseResponseDto;
import school.sptech.hub.domain.dto.livro.LivroCreateDto;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.infraestructure.persistance.AcabamentoRepository;
import school.sptech.hub.infraestructure.persistance.CategoriaRepository;
import school.sptech.hub.infraestructure.persistance.ConservacaoRepository;
import school.sptech.hub.infraestructure.persistance.LivroRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private AcabamentoRepository acabamentoRepository;

    @Autowired
    private ChatGptAdapter chatGptAdapter;

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


    public LivroComSinopseResponseDto buscarLivroPorIdComSinopse(Integer id) {
        Livro livro = repository.findById(id).orElseThrow(()-> new LivroNaoEncontradoException("O id especificado não foi encontrado"));
        String sinopse = chatGptAdapter.gerarSinopse(livro.getTitulo(), livro.getAutor());

       return LivroMapper.toComSinopseResponseDto(livro,sinopse);
    }
    public LivroResponseDto buscarLivroPorId(Integer id) {
        Livro livro = repository.findById(id).orElseThrow(()-> new LivroNaoEncontradoException("O id especificado não foi encontrado"));

        return LivroMapper.toResponseDto(livro);
    }

    public Livro atualizarLivro(Integer id, Livro livro) {
        Livro existingLivro = repository.findById(id).orElseThrow(()-> new LivroNaoEncontradoException("O id especificado não foi encontrado"));
        if(existingLivro == null){
            return null;
        }
        Livro updatedLivro = LivroMapper.updateLivroFields(existingLivro, livro);
        return repository.save(updatedLivro);
    }

    public Livro deletarLivro(Integer id) {
            Livro livro = repository.findById(id).orElseThrow(()-> new LivroNaoEncontradoException("O id especificado não foi encontrado"));
            repository.deleteById(id);
            return livro;
    }

}
