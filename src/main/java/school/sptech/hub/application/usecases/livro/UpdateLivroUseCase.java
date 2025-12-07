package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.hub.application.exceptions.AcabamentoExceptions.AcabamentoNaoEncontradoException;
import school.sptech.hub.application.exceptions.ConservacaoExceptions.ConservacaoNaoEncontradaException;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroJaExisteException;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.application.gateways.acabamento.AcabamentoGateway;
import school.sptech.hub.application.gateways.categoria.CategoriaGateway;
import school.sptech.hub.application.gateways.conservacao.ConservacaoGateway;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.entity.Acabamento;
import school.sptech.hub.domain.entity.Categoria;
import school.sptech.hub.domain.entity.Conservacao;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.dto.livro.LivroUpdateDto;

@Component
@Transactional
public class UpdateLivroUseCase {

    private final LivroGateway livroGateway;
    private final CategoriaGateway categoriaGateway;
    private final AcabamentoGateway acabamentoGateway;
    private final ConservacaoGateway conservacaoGateway;

    public UpdateLivroUseCase(LivroGateway livroGateway,
                              CategoriaGateway categoriaGateway,
                              AcabamentoGateway acabamentoGateway,
                              ConservacaoGateway conservacaoGateway) {
        this.livroGateway = livroGateway;
        this.categoriaGateway = categoriaGateway;
        this.acabamentoGateway = acabamentoGateway;
        this.conservacaoGateway = conservacaoGateway;
    }

    public LivroResponseDto execute(Integer id, LivroUpdateDto livroUpdateDto) {
        Livro existingLivro = livroGateway.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException("Livro não encontrado com ID: " + id));

        if (livroUpdateDto.getNomeCategoria() != null) {
            Categoria categoria = processarCategoria(livroUpdateDto.getNomeCategoria());
            livroUpdateDto.setNomeCategoria(categoria.getNome());
        }

        if (livroUpdateDto.getAcabamentoId() != null) {
            Acabamento acabamento = acabamentoGateway.findById(livroUpdateDto.getAcabamentoId())
                    .orElseThrow(() -> new AcabamentoNaoEncontradoException("Acabamento não encontrado com ID: " + livroUpdateDto.getAcabamentoId()));
            existingLivro.setAcabamento(acabamento);
        }

        if (livroUpdateDto.getConservacaoId() != null) {
            Conservacao conservacao = conservacaoGateway.findById(livroUpdateDto.getConservacaoId())
                    .orElseThrow(() -> new ConservacaoNaoEncontradaException("Conservação não encontrada com ID: " + livroUpdateDto.getConservacaoId()));
            existingLivro.setEstadoConservacao(conservacao);
        }

        LivroMapper.updateEntityFromDto(existingLivro, livroUpdateDto);

        existingLivro.validateUpdateRules();

        // Verificar se há mudança de ISBN e se o novo ISBN já existe
        if (livroUpdateDto.getIsbn() != null &&
            !livroUpdateDto.getIsbn().equals(existingLivro.getIsbn())) {
            livroGateway.findByIsbn(livroUpdateDto.getIsbn())
                .ifPresent(livro -> {
                    throw new LivroJaExisteException("Já existe um livro cadastrado com este ISBN.");
                });
        }

        // Salvar no repositório
        Livro savedLivro = livroGateway.updateLivro(existingLivro)
                .orElseThrow(() -> new LivroNaoEncontradoException("Erro ao atualizar livro"));

        return LivroMapper.toResponseDto(savedLivro);
    }

    /**
     * Processa a categoria do livro: se já existir, reutiliza; se não existir, cria uma nova
     * (mesma lógica do CreateLivroUseCase)
     */
    private Categoria processarCategoria(String nomeCategoria) {
        if (nomeCategoria == null || nomeCategoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório e deve ser válido");
        }

        // Normalizar o nome (trim e capitalizar primeira letra)
        String nomeNormalizado = nomeCategoria.trim();
        nomeNormalizado = nomeNormalizado.substring(0, 1).toUpperCase() +
                         nomeNormalizado.substring(1).toLowerCase();

        return categoriaGateway.findOrCreateCategoria(nomeNormalizado);
    }
}
