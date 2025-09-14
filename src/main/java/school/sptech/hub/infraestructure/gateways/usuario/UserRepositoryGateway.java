package school.sptech.hub.infraestructure.gateways.usuario;

import org.springframework.stereotype.Component;
import school.sptech.hub.application.gateways.usuario.UsuarioGateway;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioEntity;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioRepository;

import java.util.Optional;

@Component
public class UserRepositoryGateway implements UsuarioGateway {
    private final UsuarioRepository usuarioRepository;

    public UserRepositoryGateway(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<Usuario> createUsuario(Usuario usuario){
        UsuarioEntity usuarioEntity = UsuarioEntityMapper.toEntity(usuario);
        UsuarioEntity savedEntity = usuarioRepository.save(usuarioEntity);
        return Optional.of(UsuarioEntityMapper.toDomain(savedEntity));
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findByEmail(email);
        return usuarioEntity.map(UsuarioEntityMapper::toDomain);
    }

    @Override
    public Optional<Usuario> findById(Integer id) {
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(id);
        return usuarioEntity.map(UsuarioEntityMapper::toDomain);
    }

    @Override
    public Optional<Usuario> updateUsuario(Usuario usuario) {
        UsuarioEntity usuarioEntity = UsuarioEntityMapper.toEntity(usuario);
        UsuarioEntity savedEntity = usuarioRepository.save(usuarioEntity);
        return Optional.of(UsuarioEntityMapper.toDomain(savedEntity));
    }

    @Override
    public void deleteUsuario(Usuario usuario) {
        UsuarioEntity usuarioEntity = UsuarioEntityMapper.toEntity(usuario);
        usuarioRepository.delete(usuarioEntity);
    }
}
