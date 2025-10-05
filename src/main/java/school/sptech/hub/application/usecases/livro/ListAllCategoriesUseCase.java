package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.livro.LivroGateway;

import java.util.List;

@Component
public class ListAllCategoriesUseCase {

    private final LivroGateway livroGateway;

    public ListAllCategoriesUseCase(LivroGateway livroGateway) {
        this.livroGateway = livroGateway;
    }

    public List<String> execute() {
        return livroGateway.findAllDistinctCategorias();
    }
}
