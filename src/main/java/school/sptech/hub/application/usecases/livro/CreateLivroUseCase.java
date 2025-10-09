package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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

    public CreateLivroUseCase(LivroGateway livroGateway, CategoriaGateway categoriaGateway, AcabamentoGateway acabamentoGateway, ConservacaoGateway conservacaoGateway) {
        this.livroGateway = livroGateway;
        this.categoriaGateway = categoriaGateway;
        this.acabamentoGateway = acabamentoGateway;
        this.conservacaoGateway = conservacaoGateway;
    }

    @Transactional
    public LivroResponseDto execute(LivroCreateDto livroCreateDto) {
        // Processar categoria (buscar existente ou criar nova) a partir do nome
        Categoria categoria = processarCategoria(livroCreateDto.getNomeCategoria());

        Acabamento acabamento = acabamentoGateway.findById(livroCreateDto.getAcabamentoId())
                .orElseThrow(() -> new IllegalArgumentException("Acabamento não encontrado"));
        Conservacao conservacao = conservacaoGateway.findById(livroCreateDto.getConservacaoId())
                .orElseThrow(() -> new IllegalArgumentException("Conservação não encontrada"));

        // Converter DTO para entidade usando o mapper
        Livro livroEntity = LivroMapper.toEntity(livroCreateDto);

        // Sobrescrever a categoria com a processada (garantindo que seja a categoria do banco)
        livroEntity.setCategoria(categoria);
        livroEntity.setAcabamento(acabamento);
        livroEntity.setEstadoConservacao(conservacao);

        // Garantir que o campo capa está preenchido
        if (livroEntity.getCapa() == null || livroEntity.getCapa().isBlank()) {
            throw new IllegalArgumentException("O campo capa é obrigatório");
        }

        // Validação usando regras de negócio da entidade de domínio
        livroEntity.validateBusinessRules();

        // Verificar se livro com ISBN já existe
        Optional<Livro> existingLivro = livroGateway.findByIsbn(livroCreateDto.getIsbn());
        if (existingLivro.isPresent()) {
            throw new LivroJaExisteException("Já existe um livro cadastrado com este ISBN.");
        }

        // Criar o livro no banco de dados
        Livro createdLivro = livroGateway.createLivro(livroEntity)
                .orElseThrow(() -> new LivroNaoEncontradoException("Erro ao criar livro"));

        return LivroMapper.toResponseDto(createdLivro);
    }

    /**
     * Processa a categoria do livro: se já existir, reutiliza; se não existir, cria uma nova
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
