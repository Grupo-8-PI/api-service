package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.entity.Livro;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListRecommendedLivrosUseCase {

    private final LivroGateway livroGateway;

    public ListRecommendedLivrosUseCase(LivroGateway livroGateway) {
        this.livroGateway = livroGateway;
    }

    public List<LivroResponseDto> execute() {
        List<Livro> livros = livroGateway.findRecommendedRandomLivros();

        if (livros.isEmpty()) {
            throw new LivroNaoEncontradoException("Nenhum livro disponível para recomendação");
        }

        return livros.stream()
                .map(LivroMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
