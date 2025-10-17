package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.hub.application.adapter.ChatGptAdapter;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroJaExisteException;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.application.gateways.acabamento.AcabamentoGateway;
import school.sptech.hub.application.gateways.categoria.CategoriaGateway;
import school.sptech.hub.application.gateways.conservacao.ConservacaoGateway;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.entity.Acabamento;
import school.sptech.hub.domain.entity.Categoria;
import school.sptech.hub.domain.entity.Conservacao;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.dto.livro.LivroCreateDto;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;

import java.util.Optional;

@Component
public class CreateLivroUseCase {

    private final LivroGateway livroGateway;
    private final CategoriaGateway categoriaGateway;
    private final AcabamentoGateway acabamentoGateway;
    private final ConservacaoGateway conservacaoGateway;
    private final ChatGptAdapter chatGptAdapter;

    public CreateLivroUseCase(
            LivroGateway livroGateway,
            CategoriaGateway categoriaGateway,
            AcabamentoGateway acabamentoGateway,
            ConservacaoGateway conservacaoGateway,
            ChatGptAdapter chatGptAdapter) {
        this.livroGateway = livroGateway;
        this.categoriaGateway = categoriaGateway;
        this.acabamentoGateway = acabamentoGateway;
        this.conservacaoGateway = conservacaoGateway;
        this.chatGptAdapter = chatGptAdapter;
    }

    @Transactional
    public LivroResponseDto execute(LivroCreateDto livroCreateDto) {
        Optional<Livro> existingLivro = livroGateway.findByIsbn(livroCreateDto.getIsbn());
        if (existingLivro.isPresent()) {
            throw new LivroJaExisteException("Já existe um livro cadastrado com este ISBN.");
        }

        Acabamento acabamento = acabamentoGateway.findById(livroCreateDto.getAcabamentoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Acabamento com ID " + livroCreateDto.getAcabamentoId() + " não encontrado"));

        Conservacao conservacao = conservacaoGateway.findById(livroCreateDto.getConservacaoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Conservação com ID " + livroCreateDto.getConservacaoId() + " não encontrada"));

        Categoria categoria = processarCategoria(livroCreateDto.getNomeCategoria());

        Livro livroEntity = LivroMapper.toEntity(livroCreateDto);
        livroEntity.setAcabamento(acabamento);
        livroEntity.setEstadoConservacao(conservacao);
        livroEntity.setCategoria(categoria);

        livroEntity.validateBusinessRules();

        Livro createdLivro = livroGateway.createLivro(livroEntity)
                .orElseThrow(() -> new LivroNaoEncontradoException("Erro ao criar livro"));

        chatGptAdapter.gerarSinopse(createdLivro.getTitulo(), createdLivro.getAutor());

        return LivroMapper.toResponseDto(createdLivro);
    }

    private Categoria processarCategoria(String nomeCategoria) {
        if (nomeCategoria == null || nomeCategoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório e deve ser válido");
        }

        String nomeNormalizado = nomeCategoria.trim();
        nomeNormalizado = nomeNormalizado.substring(0, 1).toUpperCase() +
                         nomeNormalizado.substring(1).toLowerCase();

        return categoriaGateway.findOrCreateCategoria(nomeNormalizado);
    }
}
