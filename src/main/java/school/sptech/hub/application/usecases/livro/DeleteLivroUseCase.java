package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;

@Component
public class DeleteLivroUseCase {

    private final LivroGateway livroGateway;
    private final LivroMapper livroMapper;

    public DeleteLivroUseCase(LivroGateway livroGateway, LivroMapper livroMapper) {
        this.livroGateway = livroGateway;
        this.livroMapper = livroMapper;
    }

    public LivroResponseDto execute(Integer id) {
        Livro livro = livroGateway.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException("Livro não encontrado com ID: " + id));

        livroGateway.deleteLivro(id);

        return livroMapper.toResponseDto(livro);
    }
}
