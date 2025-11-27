package school.sptech.hub.domain.dto.categoria;

import school.sptech.hub.domain.entity.Categoria;

public class CategoriaMapper {

    public static CategoriaResponseDto toResponseDto(Categoria categoria) {
        if (categoria == null) return null;

        CategoriaResponseDto dto = new CategoriaResponseDto();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());

        return dto;
    }
}
