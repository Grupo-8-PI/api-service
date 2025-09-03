package school.sptech.hub.infraestructure.gateways.usuario;

import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioEntity;

public class UsuarioEntityMapper {
    public static UsuarioEntity toEntity(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioEntity(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getTelefone(),
            usuario.getTipo_usuario(),
            usuario.getCpf(),
            usuario.getSenha(),
            usuario.getDtNascimento()
        );
    }

    public static Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(entity.getId());
        usuario.setNome(entity.getNome());
        usuario.setEmail(entity.getEmail());
        usuario.setTelefone(entity.getTelefone());
        usuario.setTipo_usuario(entity.getTipo_usuario());
        usuario.setCpf(entity.getCpf());
        usuario.setSenha(entity.getSenha());
        usuario.setDtNascimento(entity.getDtNascimento());
        return usuario;
    }
}
