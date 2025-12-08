package school.sptech.hub.domain.dto.usuario;

import school.sptech.hub.domain.entity.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioCreateDto dto){
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setTipo_usuario(dto.getTipo_usuario());
        usuario.setCpf(dto.getCpf());
        usuario.setSenha(dto.getSenha());
        usuario.setDtNascimento(dto.getDtNascimento());
        return usuario;
    }
    public static UsuarioResponseDto toResponseDto(Usuario usuario){
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setTipo_usuario(usuario.getTipo_usuario() != null ? usuario.getTipo_usuario().toLowerCase() : null);
        dto.setCpf(usuario.getCpf());
        dto.setDtNascimento(usuario.getDtNascimento());
        return dto;
    }
    public static Usuario updateUserFields(Usuario existingUsuario, Usuario toBeUpdatedUser) {
        if (toBeUpdatedUser.getNome() != null) {
            existingUsuario.setNome(toBeUpdatedUser.getNome());
        }
        if (toBeUpdatedUser.getEmail() != null) {
            existingUsuario.setEmail(toBeUpdatedUser.getEmail());
        }
        if (toBeUpdatedUser.getTelefone() != null) {
            existingUsuario.setTelefone(toBeUpdatedUser.getTelefone());
        }
        if (toBeUpdatedUser.getTipo_usuario() != null) {
            existingUsuario.setTipo_usuario(toBeUpdatedUser.getTipo_usuario());
        }
        if (toBeUpdatedUser.getCpf() != null) {
            existingUsuario.setCpf(toBeUpdatedUser.getCpf());
        }
        if (toBeUpdatedUser.getDtNascimento() != null) {
            existingUsuario.setDtNascimento(toBeUpdatedUser.getDtNascimento());
        }
        if (toBeUpdatedUser.getSenha() != null) {
            existingUsuario.setSenha(toBeUpdatedUser.getSenha());
        }
        return existingUsuario;
    }

    public static Usuario toUsuarioLoginDto(UsuarioLoginDto usuarioLoginDto) {
        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioLoginDto.getEmail());
        usuario.setSenha(usuarioLoginDto.getSenha());

        return usuario;
    }

    public static UsuarioTokenDto toUsuarioTokenDto(Usuario usuario, String token) {
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setUserId(Long.valueOf(usuario.getId()));
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setNome(usuario.getNome());
        usuarioTokenDto.setToken(token);
        usuarioTokenDto.setCargo(usuario.getTipo_usuario());

        return usuarioTokenDto;
    }

    public static UsuarioListarDto toUsuarioListarDto(Usuario usuario) {
        UsuarioListarDto usuarioListarDto = new UsuarioListarDto();

        usuarioListarDto.setId(Long.valueOf(usuario.getId()));
        usuarioListarDto.setEmail(usuario.getEmail());
        usuarioListarDto.setNome(usuario.getNome());

        return usuarioListarDto;
    }

    public static UsuarioUpdateTokenDto toUsuarioUpdateDto(UsuarioResponseDto usuario, String token){
        UsuarioUpdateTokenDto dto = new UsuarioUpdateTokenDto();

        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setTipo_usuario(usuario.getTipo_usuario());
        dto.setCpf(usuario.getCpf());
        dto.setDtNascimento(usuario.getDtNascimento());
        dto.setToken(token);

        return dto;
    }

}
