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

    public UsuarioResponseDto getUserById(Integer id) {
        Usuario usuarioFinded = repository.findById(id).orElse(null);
        if (usuarioFinded == null) {
            return null;
        }
        return UsuarioMapper.toResponseDto(usuarioFinded);
    }

    public Usuario updateUserById(Integer id, Usuario usuario) {
        Usuario existingUser = repository.findById(id).orElse(null);
        if (existingUser == null) {
            return null;
        }
        Usuario updatedUser = UsuarioMapper.updateUserFields(existingUser, usuario);
        return repository.save(updatedUser);
    }


    private boolean isValidUserType(String userType) {
        return "admin".equalsIgnoreCase(userType) || "user".equalsIgnoreCase(userType);
    }

}
