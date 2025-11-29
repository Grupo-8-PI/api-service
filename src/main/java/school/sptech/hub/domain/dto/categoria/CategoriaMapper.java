package school.sptech.hub.domain.dto.categoria;

import school.sptech.hub.domain.entity.Categoria;

public class CategoriaMapper {

    public static Categoria toEntity(CategoriaCreateDto dto) {
        if (dto == null) return null;

        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        return categoria;
    }

    public static CategoriaResponseDto toResponseDto(Categoria categoria) {
        if (categoria == null) return null;

        CategoriaResponseDto dto = new CategoriaResponseDto();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());

        return dto;
    }
}
