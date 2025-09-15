package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroJaExisteException;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.application.gateways.categoria.CategoriaGateway;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.entity.Categoria;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.dto.livro.LivroCreateDto;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;

import java.util.Optional;

@Component
public class CreateLivroUseCase {

    private final LivroGateway livroGateway;
    private final CategoriaGateway categoriaGateway;

    public CreateLivroUseCase(LivroGateway livroGateway, CategoriaGateway categoriaGateway) {
        this.livroGateway = livroGateway;
        this.categoriaGateway = categoriaGateway;
    }

    @Transactional
    public LivroResponseDto execute(LivroCreateDto livroCreateDto) {
        // Processar categoria (buscar existente ou criar nova)
        Categoria categoria = processarCategoria(livroCreateDto.getCategoria());

        // Converter DTO para entidade usando o mapper
        Livro livroEntity = LivroMapper.toEntity(livroCreateDto);

        // Sobrescrever a categoria com a processada (garantindo que seja a categoria do banco)
        livroEntity.setCategoria(categoria);

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
    private Categoria processarCategoria(Categoria categoriaDto) {
        if (categoriaDto == null || categoriaDto.getNome() == null || categoriaDto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria é obrigatória e deve ter um nome válido");
        }

        // Se o DTO tem ID válido, tenta buscar a categoria existente primeiro
        if (categoriaDto.getId() != null && categoriaDto.getId() > 0) {
            Optional<Categoria> categoriaExistente = categoriaGateway.findById(categoriaDto.getId());
            if (categoriaExistente.isPresent()) {
                return categoriaExistente.get();
            }
        }

        // Busca ou cria categoria pelo nome normalizando primeiro
        String nomeNormalizado = categoriaDto.getNome().trim();
        nomeNormalizado = nomeNormalizado.substring(0, 1).toUpperCase() +
                         nomeNormalizado.substring(1).toLowerCase();

        return categoriaGateway.findOrCreateCategoria(nomeNormalizado);
    }
}
