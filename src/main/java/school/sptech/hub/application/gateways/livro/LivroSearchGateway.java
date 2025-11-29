package school.sptech.hub.application.gateways.livro;

import org.springframework.data.domain.Page;
import school.sptech.hub.domain.entity.Livro;

public interface LivroSearchGateway {
    Page<Livro> search(String query, int page, int size);
}
