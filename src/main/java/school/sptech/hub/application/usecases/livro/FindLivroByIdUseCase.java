package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;

@Component
public class FindLivroByIdUseCase {
    private final LivroGateway livroGateway;

    public FindLivroByIdUseCase(LivroGateway livroGateway) {
        this.livroGateway = livroGateway;
    }

    public LivroResponseDto execute(Integer id) {
        Livro livro = livroGateway.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException("Livro não encontrado com ID: " + id));

        return LivroMapper.toResponseDto(livro);
    }
}
