package school.sptech.hub.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.hub.crud.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
