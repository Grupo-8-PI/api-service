package school.sptech.hub.application.usecases.livro;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.application.service.LivroEnrichmentService;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroPaginatedResponseDto;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListLivrosPaginatedUseCase {

    private final LivroGateway livroGateway;
    private final LivroEnrichmentService livroEnrichmentService;

    public ListLivrosPaginatedUseCase(LivroGateway livroGateway, LivroEnrichmentService livroEnrichmentService) {
        this.livroGateway = livroGateway;
        this.livroEnrichmentService = livroEnrichmentService;
    }

@Cacheable(
        value = "livros",
        key = "'p=' + #page + ',s=' + #size",
        unless = "#result == null or #result.livros.isEmpty() or #bypassCache == true"
)

public LivroPaginatedResponseDto execute(int page, int size, boolean bypassCache) {
        Page<Livro> livrosPage = livroGateway.findAllPaginated(page, size);
        List<Livro> livros = livrosPage.getContent();
        livroEnrichmentService.enrichWithReservaStatus(livros);

        List<LivroResponseDto> livrosDto = livros.stream()
                .map(LivroMapper::toResponseDto)
                .collect(Collectors.toList());
        return new LivroPaginatedResponseDto(
            livrosDto,
            livrosPage.getTotalPages(),
            livrosPage.getTotalElements(),
            livrosPage.getNumber()
        );
    }
}
