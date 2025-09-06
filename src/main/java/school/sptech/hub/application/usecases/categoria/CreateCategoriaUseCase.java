package school.sptech.hub.application.usecases.categoria;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.CategoriaExceptions.CategoriaJaExisteException;
import school.sptech.hub.application.exceptions.CategoriaExceptions.CategoriaNaoEncontradaException;
import school.sptech.hub.application.gateways.categoria.CategoriaGateway;
import school.sptech.hub.domain.entity.Categoria;

import java.util.Optional;

@Component
public class CreateCategoriaUseCase {

    private final CategoriaGateway categoriaGateway;

    public CreateCategoriaUseCase(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    public Categoria execute(Categoria categoria) {
        // Validação usando regras de negócio da entidade de domínio
        categoria.validateBusinessRules();

        // Verificar se categoria com mesmo nome já existe
        Optional<Categoria> existingCategoria = categoriaGateway.findByNome(categoria.getNome());
        if (existingCategoria.isPresent()) {
            throw new CategoriaJaExisteException("Já existe uma categoria com este nome: " + categoria.getNome());
        }

        return categoriaGateway.createCategoria(categoria)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Erro ao criar categoria"));
    }
}
