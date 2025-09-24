package school.sptech.hub.infraestructure.gateways.categoria;

import school.sptech.hub.domain.entity.Categoria;
import school.sptech.hub.infraestructure.persistance.categoriaPersistance.CategoriaEntity;

public class CategoriaEntityMapper {

    public static CategoriaEntity toEntity(Categoria categoria) {
        if (categoria == null) return null;

        CategoriaEntity entity = new CategoriaEntity();
        entity.setId(categoria.getId());
        entity.setNomeCategoria(categoria.getNome());

        return entity;
    }

    public static Categoria toDomain(CategoriaEntity entity) {
        if (entity == null) return null;

        Categoria categoria = new Categoria();
        categoria.setId(entity.getId());
        categoria.setNome(entity.getNomeCategoria());

        return categoria;
    }
}
