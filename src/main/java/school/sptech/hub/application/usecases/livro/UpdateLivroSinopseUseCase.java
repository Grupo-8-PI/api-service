package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.application.service.LivroEnrichmentService;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.entity.Livro;

@Component
@Transactional
public class UpdateLivroSinopseUseCase {

    private final LivroGateway livroGateway;
    private final LivroEnrichmentService livroEnrichmentService;

    public UpdateLivroSinopseUseCase(LivroGateway livroGateway, LivroEnrichmentService livroEnrichmentService) {
        this.livroGateway = livroGateway;
        this.livroEnrichmentService = livroEnrichmentService;
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

        livroEnrichmentService.enrichWithReservaStatus(livroAtualizado);

        return LivroMapper.toResponseDto(livroAtualizado);
    }
}
