package school.sptech.hub.application.usecases.livro;

import org.springframework.stereotype.Component;
import school.sptech.hub.domain.dto.categoria.CategoriaDto;
import school.sptech.hub.infraestructure.persistance.categoriaPersistance.CategoriaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListAllCategoriesUseCase {

    private final CategoriaRepository categoriaRepository;

    public ListAllCategoriesUseCase(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDto> execute() {
        return categoriaRepository.findAll().stream()
                .map(entity -> new CategoriaDto(entity.getId(), entity.getNomeCategoria()))
                .collect(Collectors.toList());
    }
}
