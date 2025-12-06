package school.sptech.hub.application.usecases.livro;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroPaginatedResponseDto;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListLivrosPaginatedUseCase {

    private final LivroGateway livroGateway;

    public ListLivrosPaginatedUseCase(LivroGateway livroGateway) {
        this.livroGateway = livroGateway;
    }

//    @Cacheable(value = "livros", key = "#page +
@Cacheable(
        value = "livros",
        key = "'p=' + #page + ',s=' + #size",
        unless = "#result == null or #result.livros.isEmpty()"
)

public LivroPaginatedResponseDto execute(int page, int size) {
        Page<Livro> livrosPage = livroGateway.findAllPaginated(page, size);
        List<LivroResponseDto> livros = livrosPage.getContent().stream()
                .map(LivroMapper::toResponseDto)
                .collect(Collectors.toList());
        return new LivroPaginatedResponseDto(
            livros,
            livrosPage.getTotalPages(),
            livrosPage.getTotalElements(),
            livrosPage.getNumber()
        );
    }
}
