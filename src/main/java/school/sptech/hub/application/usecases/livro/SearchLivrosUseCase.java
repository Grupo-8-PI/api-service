package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroPaginatedResponseDto;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.infraestructure.search.LivroSearchService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchLivrosUseCase {

    private final LivroSearchService searchService;
    private final LivroGateway livroGateway;

    public SearchLivrosUseCase(LivroSearchService searchService, LivroGateway livroGateway) {
        this.searchService = searchService;
        this.livroGateway = livroGateway;
    }

    public LivroPaginatedResponseDto execute(String query, int page, int size) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Termo de busca não pode ser vazio");
        }

        // Realizar busca no índice Lucene
        LivroSearchService.SearchResult searchResult = searchService.searchLivros(query.trim(), page, size);

        // Buscar livros pelo ID no banco de dados
        List<LivroResponseDto> livros = searchResult.getLivroIds().stream()
                .map(livroGateway::findById)
                .filter(optional -> optional.isPresent())
                .map(optional -> optional.get())
                .map(LivroMapper::toResponseDto)
                .collect(Collectors.toList());

        // Criar resposta paginada com a ordem correta: livros, totalPages, totalElements, currentPage
        return new LivroPaginatedResponseDto(
                livros,
                searchResult.getTotalPages(),
                searchResult.getTotalElements(),
                searchResult.getCurrentPage()
        );
    }
}
