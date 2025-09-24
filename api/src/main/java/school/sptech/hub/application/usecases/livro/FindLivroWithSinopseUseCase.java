package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.adapter.ChatGptAdapter;
import school.sptech.hub.application.exceptions.LivroExceptions.LivroNaoEncontradoException;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.dto.livro.LivroComSinopseResponseDto;
import school.sptech.hub.domain.dto.livro.LivroMapper;

@Component
public class FindLivroWithSinopseUseCase {

    private final LivroGateway livroGateway;
    private final ChatGptAdapter chatGptAdapter;

    public FindLivroWithSinopseUseCase(LivroGateway livroGateway, ChatGptAdapter chatGptAdapter) {
        this.livroGateway = livroGateway;
        this.chatGptAdapter = chatGptAdapter;
    }

    public LivroComSinopseResponseDto execute(Integer id) {
        Livro livro = livroGateway.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException("Livro não encontrado com ID: " + id));

        String sinopse = chatGptAdapter.gerarSinopse(livro.getTitulo(), livro.getAutor());

        return LivroMapper.toComSinopseResponseDto(livro, sinopse);
    }
}
