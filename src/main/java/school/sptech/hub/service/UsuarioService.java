package school.sptech.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.hub.controller.dto.UsuarioCreateDto;
import school.sptech.hub.controller.dto.UsuarioMapper;
import school.sptech.hub.controller.dto.UsuarioResponseDto;
import school.sptech.hub.entity.Usuario;
import school.sptech.hub.exceptions.UsuarioExceptions.TipoUsuarioInvalidoException;
import school.sptech.hub.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public UsuarioResponseDto createUser(UsuarioCreateDto usuario) {
        if(!isValidUserType(usuario.getTipo_usuario())){
            throw new TipoUsuarioInvalidoException("Tipo de usuário inválido.");
        }
        Usuario usuarioEntity = UsuarioMapper.toEntity(usuario);
        Usuario createdUser = repository.save(usuarioEntity);
        return UsuarioMapper.toResponseDto(createdUser);
    }

    public UsuarioResponseDto getUserById(Integer id) {
        Usuario usuarioFinded = repository.findById(id).orElseThrow(()-> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        return UsuarioMapper.toResponseDto(usuarioFinded);
    }

    public Usuario updateUserById(Integer id, Usuario usuario) {
        Usuario existingUser = repository.findById(id).orElseThrow(()-> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        Usuario updatedUser = UsuarioMapper.updateUserFields(existingUser, usuario);
        return repository.save(updatedUser);
    }

    public Usuario deleteUserById(Integer id) {
        Usuario usuarioFinded = repository.findById(id).orElseThrow(()-> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        repository.delete(usuarioFinded);
        return usuarioFinded;
    }


    private boolean isValidUserType(String userType) {
        return "admin".equalsIgnoreCase(userType) || "cliente".equalsIgnoreCase(userType);
    }

}
