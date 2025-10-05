package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;

@Component
public class GetRandomLivroUseCase {

    private final LivroGateway livroGateway;

    public GetRandomLivroUseCase(LivroGateway livroGateway) {
        this.livroGateway = livroGateway;
    }

    public LivroResponseDto execute() {
        Livro livro = livroGateway.findRandomLivro()
                .orElseThrow(() -> new LivroNaoEncontradoException("Nenhum livro encontrado no banco de dados"));

        return LivroMapper.toResponseDto(livro);
    }
}

