package school.sptech.hub.application.service;

import org.springframework.stereotype.Service;
import school.sptech.hub.application.gateways.venda.VendaGateway;
import school.sptech.hub.domain.entity.Livro;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LivroEnrichmentService {

    private final VendaGateway vendaGateway;

    public LivroEnrichmentService(VendaGateway vendaGateway) {
        this.vendaGateway = vendaGateway;
    }

    public void enrichWithReservaStatus(Livro livro) {
        if (livro != null && livro.getId() != null) {
            boolean hasReserva = vendaGateway.hasReservaByLivroId(livro.getId());
            livro.setHasReserva(hasReserva);
        }
    }

    public void enrichWithReservaStatus(List<Livro> livros) {
        if (livros == null || livros.isEmpty()) {
            return;
        }

        Set<Integer> livroIds = livros.stream()
                .filter(livro -> livro != null && livro.getId() != null)
                .map(Livro::getId)
                .collect(Collectors.toSet());

        if (livroIds.isEmpty()) {
            // Se não há IDs válidos, marca todos como false
            livros.forEach(livro -> {
                if (livro != null) {
                    livro.setHasReserva(false);
                }
            });
            return;
        }

        Set<Integer> livrosComReserva = vendaGateway.findLivroIdsWithReservas(livroIds);

        livros.forEach(livro -> {
            if (livro != null && livro.getId() != null) {
                boolean hasReserva = livrosComReserva.contains(livro.getId());
                livro.setHasReserva(hasReserva);
            }
        });
    }
}

