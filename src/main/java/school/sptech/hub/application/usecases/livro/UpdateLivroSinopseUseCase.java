package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.entity.Livro;

@Component
@Transactional
public class UpdateLivroSinopseUseCase {

    private final LivroGateway livroGateway;

    public UpdateLivroSinopseUseCase(LivroGateway livroGateway) {
        this.livroGateway = livroGateway;
    }

    public LivroResponseDto execute(Integer id, String sinopse) {
        if (id == null) {
            throw new IllegalArgumentException("ID do livro não pode ser nulo");
        }

        if (sinopse == null || sinopse.trim().isEmpty()) {
            throw new IllegalArgumentException("Sinopse não pode ser vazia");
        }

        Livro livro = livroGateway.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException("Livro não encontrado com ID: " + id));

        livro.setDescricao(sinopse.trim());

        Livro livroAtualizado = livroGateway.updateLivro(livro)
                .orElseThrow(() -> new LivroNaoEncontradoException("Erro ao atualizar sinopse do livro"));

        return LivroMapper.toResponseDto(livroAtualizado);
    }
}

