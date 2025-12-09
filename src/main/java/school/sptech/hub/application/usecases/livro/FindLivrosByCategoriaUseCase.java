package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.CategoriaExceptions.CategoriaNaoEncontradaException;
import school.sptech.hub.application.gateways.categoria.CategoriaGateway;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.application.service.LivroEnrichmentService;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.entity.Categoria;
import school.sptech.hub.domain.entity.Livro;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindLivrosByCategoriaUseCase {

    private final LivroGateway livroGateway;
    private final CategoriaGateway categoriaGateway;
    private final LivroEnrichmentService livroEnrichmentService;

    public FindLivrosByCategoriaUseCase(LivroGateway livroGateway, CategoriaGateway categoriaGateway, LivroEnrichmentService livroEnrichmentService) {
        this.livroGateway = livroGateway;
        this.categoriaGateway = categoriaGateway;
        this.livroEnrichmentService = livroEnrichmentService;
    }

    public List<LivroResponseDto> execute(Integer categoriaId) {
        if (categoriaId == null) {
            throw new IllegalArgumentException("ID da categoria não pode ser nulo");
        }

        // Validar se a categoria existe
        Categoria categoria = categoriaGateway.findById(categoriaId)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada com ID: " + categoriaId));

        // Buscar livros por categoria
        List<Livro> livros = livroGateway.findByCategoriaId(categoriaId);
        livroEnrichmentService.enrichWithReservaStatus(livros);

        // Converter para DTOs de resposta
        return livros.stream()
                .map(LivroMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
