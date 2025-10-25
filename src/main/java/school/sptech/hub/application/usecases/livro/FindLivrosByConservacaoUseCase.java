package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.entity.TipoConservacao;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindLivrosByConservacaoUseCase {

    private final LivroGateway livroGateway;

    public FindLivrosByConservacaoUseCase(LivroGateway livroGateway) {
        this.livroGateway = livroGateway;
    }

    public List<LivroResponseDto> execute(Integer conservacaoId) {
        if (conservacaoId == null) {
            throw new IllegalArgumentException("ID da conservação não pode ser nulo");
        }

        // Validar se o ID da conservação é válido usando o enum TipoConservacao
        try {
            TipoConservacao.fromId(conservacaoId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID da conservação inválido: " + conservacaoId + ". " + e.getMessage());
        }

        // Buscar livros por conservação
        List<Livro> livros = livroGateway.findByConservacaoId(conservacaoId);

        // Converter para DTOs de resposta
        return livros.stream()
                .map(LivroMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
