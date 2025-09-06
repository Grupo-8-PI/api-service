package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.AcabamentoExceptions.AcabamentoNaoEncontradoException;
import school.sptech.hub.application.exceptions.CategoriaExceptions.CategoriaNaoEncontradaException;
import school.sptech.hub.application.exceptions.ConservacaoExceptions.ConservacaoNaoEncontradaException;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroJaExisteException;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.application.gateways.acabamento.AcabamentoGateway;
import school.sptech.hub.application.gateways.categoria.CategoriaGateway;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.entity.Acabamento;
import school.sptech.hub.domain.entity.Categoria;
import school.sptech.hub.domain.entity.Conservacao;
import school.sptech.hub.domain.dto.livro.LivroCreateDto;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.application.gateways.conservacao.ConservacaoGateway;

import java.util.Optional;

@Component
public class CreateLivroUseCase {

    private final LivroGateway livroGateway;
    private final AcabamentoGateway acabamentoGateway;
    private final CategoriaGateway categoriaGateway;
    private final ConservacaoGateway conservacaoGateway;

    public CreateLivroUseCase(LivroGateway livroGateway,
                             AcabamentoGateway acabamentoGateway,
                             CategoriaGateway categoriaGateway,
                             ConservacaoGateway conservacaoGateway) {
        this.livroGateway = livroGateway;
        this.acabamentoGateway = acabamentoGateway;
        this.categoriaGateway = categoriaGateway;
        this.conservacaoGateway = conservacaoGateway;
    }

    public LivroResponseDto execute(LivroCreateDto livroCreateDto) {
        // Buscar entidades relacionadas pelos IDs
        Acabamento acabamento = acabamentoGateway.findById(livroCreateDto.getAcabamentoId())
                .orElseThrow(() -> new AcabamentoNaoEncontradoException("Acabamento não encontrado com ID: " + livroCreateDto.getAcabamentoId()));

        Categoria categoria = categoriaGateway.findById(livroCreateDto.getCategoriaId())
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada com ID: " + livroCreateDto.getCategoriaId()));

        Conservacao conservacao = conservacaoGateway.findById(livroCreateDto.getConservacaoId())
                .orElseThrow(() -> new ConservacaoNaoEncontradaException("Conservação não encontrada com ID: " + livroCreateDto.getConservacaoId()));

        // Converter DTO para entidade usando o mapper atualizado
        Livro livroEntity = LivroMapper.toEntity(livroCreateDto, acabamento, conservacao, categoria);

        // Validação usando regras de negócio da entidade de domínio
        livroEntity.validateBusinessRules();

        // Verificar se livro com ISBN já existe
        Optional<Livro> existingLivro = livroGateway.findByIsbn(livroCreateDto.getIsbn());
        if (existingLivro.isPresent()) {
            throw new LivroJaExisteException("Já existe um livro cadastrado com este ISBN.");
        }

        Livro createdLivro = livroGateway.createLivro(livroEntity)
                .orElseThrow(() -> new LivroNaoEncontradoException("Erro ao criar livro"));

        return LivroMapper.toResponseDto(createdLivro);
    }
}
