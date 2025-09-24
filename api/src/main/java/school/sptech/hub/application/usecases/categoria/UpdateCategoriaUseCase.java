package school.sptech.hub.application.usecases.categoria;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.CategoriaExceptions.CategoriaNaoEncontradaException;
import school.sptech.hub.application.gateways.categoria.CategoriaGateway;
import school.sptech.hub.domain.entity.Categoria;

@Component
public class UpdateCategoriaUseCase {

    private final CategoriaGateway categoriaGateway;

    public UpdateCategoriaUseCase(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    public Categoria execute(Integer id, Categoria categoriaToUpdate) {
        Categoria existingCategoria = categoriaGateway.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada com ID: " + id));

        // Validação usando regras de negócio da entidade de domínio
        categoriaToUpdate.validateUpdateRules();

        // Atualizar campos se fornecidos
        if (categoriaToUpdate.getNome() != null) {
            existingCategoria.setNome(categoriaToUpdate.getNome());
        }

        return categoriaGateway.updateCategoria(existingCategoria)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Erro ao atualizar categoria"));
    }
}
