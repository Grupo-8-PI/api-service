package school.sptech.hub.controller.dto;

import school.sptech.hub.entity.Usuario;

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
        dto.setTipo_usuario(usuario.getTipo_usuario());
        dto.setCpf(usuario.getCpf());
        dto.setDtNascimento(usuario.getDtNascimento());
        return dto;
    }
    public static Usuario updateUserFields(Usuario existingUsuario, Usuario toBeUpdatedUser) {
        existingUsuario.setNome(toBeUpdatedUser.getNome());
        existingUsuario.setEmail(toBeUpdatedUser.getEmail());
        existingUsuario.setTelefone(toBeUpdatedUser.getTelefone());
        existingUsuario.setTipo_usuario(toBeUpdatedUser.getTipo_usuario());
        existingUsuario.setCpf(toBeUpdatedUser.getCpf());
        existingUsuario.setDtNascimento(toBeUpdatedUser.getDtNascimento());
        existingUsuario.setSenha(toBeUpdatedUser.getSenha());
        return existingUsuario;
    }
}
