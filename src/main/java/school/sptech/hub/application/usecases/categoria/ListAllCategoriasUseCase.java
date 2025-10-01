package school.sptech.hub.application.usecases.categoria;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.categoria.CategoriaGateway;
import school.sptech.hub.domain.dto.categoria.CategoriaMapper;
import school.sptech.hub.domain.dto.categoria.CategoriaResponseDto;
import school.sptech.hub.domain.entity.Categoria;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListAllCategoriasUseCase {

    private final CategoriaGateway categoriaGateway;

    public ListAllCategoriasUseCase(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    public List<CategoriaResponseDto> execute() {
        List<Categoria> categorias = categoriaGateway.findAll();

        return categorias.stream()
                .map(CategoriaMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
