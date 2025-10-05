package school.sptech.hub.application.usecases.categoria;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.exceptions.CategoriaExceptions.CategoriaNaoEncontradaException;
import school.sptech.hub.application.gateways.categoria.CategoriaGateway;
import school.sptech.hub.domain.dto.categoria.CategoriaMapper;
import school.sptech.hub.domain.dto.categoria.CategoriaResponseDto;
import school.sptech.hub.domain.entity.Categoria;

@Component
public class FindCategoriaByIdUseCase {

    private final CategoriaGateway categoriaGateway;

    public FindCategoriaByIdUseCase(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    public CategoriaResponseDto execute(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID da categoria não pode ser nulo");
        }

        Categoria categoria = categoriaGateway.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada com ID: " + id));

        return CategoriaMapper.toResponseDto(categoria);
    }
}
