package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.entity.Livro;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListRecentLivrosUseCase {

    private final LivroGateway livroGateway;

    public ListRecentLivrosUseCase(LivroGateway livroGateway) {
        this.livroGateway = livroGateway;
    }

    public List<LivroResponseDto> execute() {
        List<Livro> recentLivros = livroGateway.findTop3ByOrderByDataAdicaoDesc();

        return recentLivros.stream()
                .map(LivroMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
