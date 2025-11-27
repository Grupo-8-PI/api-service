package school.sptech.hub.application.service;

import org.springframework.stereotype.Service;
import school.sptech.hub.application.usecases.categoria.CreateCategoriaUseCase;
import school.sptech.hub.application.usecases.categoria.FindCategoriaByIdUseCase;
import school.sptech.hub.application.usecases.categoria.ListAllCategoriasUseCase;
import school.sptech.hub.domain.dto.categoria.CategoriaCreateDto;
import school.sptech.hub.domain.dto.categoria.CategoriaMapper;
import school.sptech.hub.domain.dto.categoria.CategoriaResponseDto;
import school.sptech.hub.domain.entity.Categoria;

import java.util.List;

@Service
public class CategoriaService {

    private final FindCategoriaByIdUseCase findCategoriaByIdUseCase;
    private final ListAllCategoriasUseCase listAllCategoriasUseCase;
    private final CreateCategoriaUseCase createCategoriaUseCase;

    public CategoriaService(FindCategoriaByIdUseCase findCategoriaByIdUseCase,
                           ListAllCategoriasUseCase listAllCategoriasUseCase,
                           CreateCategoriaUseCase createCategoriaUseCase) {
        this.findCategoriaByIdUseCase = findCategoriaByIdUseCase;
        this.listAllCategoriasUseCase = listAllCategoriasUseCase;
        this.createCategoriaUseCase = createCategoriaUseCase;
    }

    public List<CategoriaResponseDto> listarCategorias() {
        return listAllCategoriasUseCase.execute();
    }

    public CategoriaResponseDto buscarCategoriaPorId(Integer id) {
        return findCategoriaByIdUseCase.execute(id);
    }

    public CategoriaResponseDto criarCategoria(CategoriaCreateDto categoriaDto) {
        Categoria categoria = CategoriaMapper.toEntity(categoriaDto);
        Categoria categoriaCriada = createCategoriaUseCase.execute(categoria);
        return CategoriaMapper.toResponseDto(categoriaCriada);
    }
}
