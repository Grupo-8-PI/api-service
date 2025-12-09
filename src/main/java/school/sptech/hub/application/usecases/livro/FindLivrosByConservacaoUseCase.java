package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.application.service.LivroEnrichmentService;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.entity.TipoConservacao;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindLivrosByConservacaoUseCase {

    private final LivroGateway livroGateway;
    private final LivroEnrichmentService livroEnrichmentService;

    public FindLivrosByConservacaoUseCase(LivroGateway livroGateway, LivroEnrichmentService livroEnrichmentService) {
        this.livroGateway = livroGateway;
        this.livroEnrichmentService = livroEnrichmentService;
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
        livroEnrichmentService.enrichWithReservaStatus(livros);

        // Converter para DTOs de resposta
        return livros.stream()
                .map(LivroMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
