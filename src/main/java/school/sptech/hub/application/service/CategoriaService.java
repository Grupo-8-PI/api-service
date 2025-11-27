package school.sptech.hub.application.service;

import org.springframework.stereotype.Service;
import school.sptech.hub.application.usecases.categoria.FindCategoriaByIdUseCase;
import school.sptech.hub.application.usecases.categoria.ListAllCategoriasUseCase;
import school.sptech.hub.domain.dto.categoria.CategoriaResponseDto;

import java.util.List;

@Service
public class CategoriaService {

    private final FindCategoriaByIdUseCase findCategoriaByIdUseCase;
    private final ListAllCategoriasUseCase listAllCategoriasUseCase;

    public CategoriaService(FindCategoriaByIdUseCase findCategoriaByIdUseCase,
                           ListAllCategoriasUseCase listAllCategoriasUseCase) {
        this.findCategoriaByIdUseCase = findCategoriaByIdUseCase;
        this.listAllCategoriasUseCase = listAllCategoriasUseCase;
    }

    public List<CategoriaResponseDto> listarCategorias() {
        return listAllCategoriasUseCase.execute();
    }

    public CategoriaResponseDto buscarCategoriaPorId(Integer id) {
        return findCategoriaByIdUseCase.execute(id);
    }
}
