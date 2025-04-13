package school.sptech.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.hub.controller.dto.UsuarioCreateDto;
import school.sptech.hub.controller.dto.UsuarioMapper;
import school.sptech.hub.controller.dto.UsuarioResponseDto;
import school.sptech.hub.entity.Usuario;
import school.sptech.hub.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public UsuarioResponseDto createUser(UsuarioCreateDto usuario) {
        if(!isValidUserType(usuario.getTipo_usuario())){
            return null;
        }
        Usuario usuarioEntity = UsuarioMapper.toEntity(usuario);
        Usuario createdUser = repository.save(usuarioEntity);
        return UsuarioMapper.toResponseDto(createdUser);
    }

    public Usuario getUserById(Integer id) {
        return repository.findById(id)
                .orElse(null);
    }

    public Usuario updateUserById(Integer id, Usuario usuario) {
        Usuario existingUser = repository.findById(id).orElse(null);
        if (existingUser == null) {
            return null;
        }
//        Usuario usuarioUpdated = updateUserFields(existingUser, usuario);
        return repository.save(existingUser);
    }


    private boolean isValidUserType(String userType) {
        return "admin".equalsIgnoreCase(userType) || "user".equalsIgnoreCase(userType);
    }

}
