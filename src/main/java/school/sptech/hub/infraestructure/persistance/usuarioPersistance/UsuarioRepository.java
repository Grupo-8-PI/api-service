package school.sptech.hub.infraestructure.persistance.usuarioPersistance;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.hub.domain.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    Optional<Usuario> findByEmail(String email);
}
