package school.sptech.hub.application.usecases.categoria;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.categoria.CategoriaGateway;
import school.sptech.hub.domain.entity.Categoria;

import java.util.List;

@Component
public class ListAllCategoriasUseCase {

    private final CategoriaGateway categoriaGateway;

    public ListAllCategoriasUseCase(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    public List<Categoria> execute() {
        return categoriaGateway.findAll();
    }
}
