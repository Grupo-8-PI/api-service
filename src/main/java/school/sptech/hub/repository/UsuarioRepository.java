package school.sptech.hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.hub.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
