package school.sptech.hub.infraestructure.gateways.usuario;

import school.sptech.hub.application.gateways.usuario.UsuarioGateway;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioEntity;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioRepository;

import java.util.Optional;

public class UserRepositoryGateway implements UsuarioGateway {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioEntityMapper usuarioMapper;

    public UserRepositoryGateway(UsuarioRepository usuarioRepository, UsuarioEntityMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public Optional<Usuario> createUsuario(Usuario usuario){
        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuario);
        UsuarioEntity savedEntity = usuarioRepository.save(usuarioEntity);
        return Optional.of(usuarioMapper.toDomain(savedEntity));
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findByEmail(email);
        return usuarioEntity.map(usuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> findById(Integer id) {
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(id);
        return usuarioEntity.map(usuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> updateUsuario(Usuario usuario) {
        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuario);
        UsuarioEntity savedEntity = usuarioRepository.save(usuarioEntity);
        return Optional.of(usuarioMapper.toDomain(savedEntity));
    }

    @Override
    public void deleteUsuario(Usuario usuario) {
        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuario);
        usuarioRepository.delete(usuarioEntity);
    }
}
