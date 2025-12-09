package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.application.service.LivroEnrichmentService;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.entity.Livro;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListRecommendedLivrosUseCase {

    private final LivroGateway livroGateway;
    private final LivroEnrichmentService livroEnrichmentService;

    public ListRecommendedLivrosUseCase(LivroGateway livroGateway, LivroEnrichmentService livroEnrichmentService) {
        this.livroGateway = livroGateway;
        this.livroEnrichmentService = livroEnrichmentService;
    }

    public List<LivroResponseDto> execute() {
        List<Livro> livros = livroGateway.findRecommendedRandomLivros();

        if (livros.isEmpty()) {
            throw new LivroNaoEncontradoException("Nenhum livro disponível para recomendação");
        }

        livroEnrichmentService.enrichWithReservaStatus(livros);

        return livros.stream()
                .map(LivroMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
