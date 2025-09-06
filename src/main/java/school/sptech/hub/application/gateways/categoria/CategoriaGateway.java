package school.sptech.hub.application.gateways.categoria;

import school.sptech.hub.domain.entity.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaGateway {
    Optional<Categoria> createCategoria(Categoria categoria);
    Optional<Categoria> findById(Integer id);
    List<Categoria> findAll();
    Optional<Categoria> updateCategoria(Categoria categoria);
    void deleteCategoria(Integer id);
    Optional<Categoria> findByNome(String nome);
}
