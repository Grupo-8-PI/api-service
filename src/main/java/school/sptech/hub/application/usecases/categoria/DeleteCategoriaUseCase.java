package school.sptech.hub.application.usecases.categoria;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.CategoriaExceptions.CategoriaNaoEncontradaException;
import school.sptech.hub.application.gateways.categoria.CategoriaGateway;
import school.sptech.hub.domain.entity.Categoria;

@Component
public class DeleteCategoriaUseCase {

    private final CategoriaGateway categoriaGateway;

    public DeleteCategoriaUseCase(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    public Categoria execute(Integer id) {
        Categoria categoria = categoriaGateway.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada com ID: " + id));

        categoriaGateway.deleteCategoria(id);

        return categoria;
    }
}
