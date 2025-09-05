package school.sptech.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.hub.adapter.ChatGptAdapter;
import school.sptech.hub.controller.dto.livro.LivroComSinopseResponseDto;
import school.sptech.hub.controller.dto.livro.LivroCreateDto;
import school.sptech.hub.controller.dto.livro.LivroMapper;
import school.sptech.hub.controller.dto.livro.LivroResponseDto;
import school.sptech.hub.entity.Livro;
import school.sptech.hub.exceptions.LivroExceptions.LivroNaoEncontradoException;
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
    private ChatGptAdapter chatGptAdapter;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ConservacaoRepository conservacaoRepository;


    @Transactional
    public Livro createNewLivro(LivroCreateDto livro) {

        if (livro == null || livro.getTitulo() == null || livro.getTitulo().isBlank()){
            throw new IllegalArgumentException("Dados do livro inválidos.");
        }

        Livro livroEntity = LivroMapper.toEntity(livro);
        return repository.save(livroEntity);
    }

    @Transactional(readOnly = true)
    public List<LivroResponseDto> listarLivros() {
        List<Livro> livros = repository.findAll();
        List<LivroResponseDto> livrosResponse = new ArrayList<>();

        for (Livro livroAtual : livros){
            if (livroAtual != null){
                livrosResponse.add(LivroMapper.toResponseDto(livroAtual));
            }
        }
        return livrosResponse;
    }


    @Transactional(readOnly = true)
    public LivroComSinopseResponseDto buscarLivroPorIdComSinopse(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        Livro livro = repository.findById(id)
                .orElseThrow(()-> new LivroNaoEncontradoException("Livro não encontrado."));

        String sinopse = chatGptAdapter.gerarSinopse(livro.getTitulo(), livro.getAutor());
        if (sinopse == null || sinopse.isBlank()) {
            sinopse = "Sinopse indisponível no momento.";
        }

        return LivroMapper.toComSinopseResponseDto(livro, sinopse);
    }

    @Transactional(readOnly = true)
    public LivroResponseDto buscarLivroPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        Livro livro = repository.findById(id)
                .orElseThrow(()-> new LivroNaoEncontradoException("Livro não encontrado."));

        return LivroMapper.toResponseDto(livro);
    }

    @Transactional
    public Livro atualizarLivro(Integer id, Livro livro) {

        if (id == null || id <= 0 || livro == null) {
            throw new IllegalArgumentException("Dados inválidos para atualização.");
        }

        Livro livroExistente = repository.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException("Livro não encontrado."));

        Livro livroAtualizado = LivroMapper.updateLivroFields(livroExistente, livro);
        return repository.save(livroAtualizado);
    }

    @Transactional
    public Livro deletarLivro(Integer id) {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("ID inválido.");
            }

            Livro livro = repository.findById(id)
                    .orElseThrow(() -> new LivroNaoEncontradoException("Livro não encontrado."));

            repository.deleteById(id);
            return livro;
    }

}
