package school.sptech.hub.infraestructure.gateways.usuario;

import school.sptech.hub.application.gateways.usuario.UsuarioGateway;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioEntity;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioRepository;

public class UserRepositoryGateway implements UsuarioGateway {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioEntityMapper usuarioMapper;

    public UserRepositoryGateway(UsuarioRepository usuarioRepository, UsuarioEntityMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public Usuario createUsuario(Usuario usuario){
        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuario);
        UsuarioEntity savedEntity = usuarioRepository.save(usuarioEntity);

        return usuarioMapper.toDomain(savedEntity);
    }
}
