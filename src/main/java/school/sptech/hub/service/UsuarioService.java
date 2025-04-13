package school.sptech.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.hub.entity.Usuario;
import school.sptech.hub.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario createUser(Usuario usuario) {
        usuario.setId(null);
        if (isValidUserType(usuario.getTipo_usuario())) {
            return repository.save(usuario);
        }
        return null;
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
        Usuario usuarioUpdated = updateUserFields(existingUser, usuario);
        return repository.save(usuarioUpdated);
    }


    private boolean isValidUserType(String userType) {
        return "admin".equalsIgnoreCase(userType) || "user".equalsIgnoreCase(userType);
    }

    private Usuario updateUserFields(Usuario existingUsuario, Usuario toBeUpdatedUser) {
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
}
