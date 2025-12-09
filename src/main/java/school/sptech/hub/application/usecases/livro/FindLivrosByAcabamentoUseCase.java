package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.livro.LivroGateway;
import school.sptech.hub.application.service.LivroEnrichmentService;
import school.sptech.hub.domain.dto.livro.LivroMapper;
import school.sptech.hub.domain.dto.livro.LivroResponseDto;
import school.sptech.hub.domain.entity.Livro;
import school.sptech.hub.domain.entity.TipoAcabamento;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindLivrosByAcabamentoUseCase {

    private final LivroGateway livroGateway;
    private final LivroEnrichmentService livroEnrichmentService;

    public FindLivrosByAcabamentoUseCase(LivroGateway livroGateway, LivroEnrichmentService livroEnrichmentService) {
        this.livroGateway = livroGateway;
        this.livroEnrichmentService = livroEnrichmentService;
    }

    public List<LivroResponseDto> execute(Integer acabamentoId) {
        if (acabamentoId == null) {
            throw new IllegalArgumentException("ID do acabamento não pode ser nulo");
        }

        // Validar se o ID do acabamento é válido usando o enum TipoAcabamento
        try {
            TipoAcabamento.fromId(acabamentoId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID do acabamento inválido: " + acabamentoId + ". " + e.getMessage());
        }

        // Buscar livros por acabamento
        List<Livro> livros = livroGateway.findByAcabamentoId(acabamentoId);
        livroEnrichmentService.enrichWithReservaStatus(livros);

        // Converter para DTOs de resposta
        return livros.stream()
                .map(LivroMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
