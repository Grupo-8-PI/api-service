package school.sptech.hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.hub.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
}
