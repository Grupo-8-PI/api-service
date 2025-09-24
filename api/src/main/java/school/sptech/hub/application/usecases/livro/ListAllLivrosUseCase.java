package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListAllLivrosUseCase {

    private final LivroGateway livroGateway;

    public ListAllLivrosUseCase(LivroGateway livroGateway) {
        this.livroGateway = livroGateway;
    }

    public List<LivroResponseDto> execute() {
        List<Livro> livros = livroGateway.findAll();

        return livros.stream()
                .map(LivroMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
