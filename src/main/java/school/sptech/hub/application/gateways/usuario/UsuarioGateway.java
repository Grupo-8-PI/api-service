package school.sptech.hub.application.gateways.usuario;

import school.sptech.hub.domain.entity.Usuario;

import java.util.Optional;

public interface UsuarioGateway {
    Optional<Usuario> createUsuario(Usuario usuario);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findById(Integer id);
    Optional<Usuario> updateUsuario(Usuario usuario);
    void deleteUsuario(Usuario usuario);
}
