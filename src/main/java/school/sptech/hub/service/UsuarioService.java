package school.sptech.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.hub.config.GerenciadorTokenJwt;
import school.sptech.hub.controller.dto.usuario.*;
import school.sptech.hub.entity.Usuario;
import school.sptech.hub.exceptions.UsuarioExceptions.TipoUsuarioInvalidoException;
import school.sptech.hub.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.repository.UsuarioRepository;

import static school.sptech.hub.validators.UsuarioValidator.isValidUserType;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public UsuarioResponseDto createUser(UsuarioCreateDto usuario) {
        if(!isValidUserType(usuario.getTipo_usuario())){
            throw new TipoUsuarioInvalidoException("Tipo de usuário inválido.");
        }

        if(usuario.getSenha() == null || usuario.getSenha().isBlank()){
            throw new IllegalArgumentException("Senha inválida.");
        }

        Usuario usuarioEntity = UsuarioMapper.toEntity(usuario);

        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuarioEntity.setSenha(senhaCriptografada);

        Usuario createdUser = repository.save(usuarioEntity);

        return UsuarioMapper.toResponseDto(createdUser);
    }

    @Transactional(readOnly = true)
    public UsuarioTokenDto autenticar(Usuario usuario) {

        if (usuario.getEmail() == null || usuario.getEmail().isBlank() || usuario.getSenha() == null || usuario.getSenha().isBlank()){
            throw new IllegalArgumentException("Credenciais inválidas.");
        }

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuario.getEmail(), usuario.getSenha()
        );

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =
                repository.findByEmail(usuario.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(401, "Credenciais inválidas.", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.toUsuarioTokenDto(usuarioAutenticado, token);
    }

    public UsuarioResponseDto getUserById(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        Usuario usuarioFinded = repository.findById(id)
                .orElseThrow(()-> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        return UsuarioMapper.toResponseDto(usuarioFinded);
    }

    @Transactional
    public UsuarioUpdateTokenDto updateUserById(Integer id, Usuario usuario) {

        if (id == null || id <= 0 || usuario == null) {
            throw new IllegalArgumentException("Dados inválidos para atualização.");
        }

        Usuario existingUser = repository.findById(id)
                .orElseThrow(()-> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        Usuario updatedUser = UsuarioMapper.updateUserFields(existingUser, usuario);

        if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
            String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
            updatedUser.setSenha(senhaCriptografada);
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                updatedUser.getEmail(), updatedUser.getSenha()
        );

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        repository.save(updatedUser);

        UsuarioResponseDto usuarioResponseDto = UsuarioMapper.toResponseDto(updatedUser);

        return UsuarioMapper.toUsuarioUpdateDto(usuarioResponseDto, token);
    }

    @Transactional
    public Usuario deleteUserById(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        Usuario usuarioFinded = repository.findById(id)
                .orElseThrow(()-> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        repository.delete(usuarioFinded);

        usuarioFinded.setSenha(null);
        return usuarioFinded;
    }


}
