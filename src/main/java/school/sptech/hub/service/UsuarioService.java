package school.sptech.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.hub.config.GerenciadorTokenJwt;
import school.sptech.hub.controller.dto.*;
import school.sptech.hub.entity.Usuario;
import school.sptech.hub.exceptions.UsuarioExceptions.TipoUsuarioInvalidoException;
import school.sptech.hub.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.repository.UsuarioRepository;

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

    public UsuarioResponseDto createUser(UsuarioCreateDto usuario) {
        if(!isValidUserType(usuario.getTipo_usuario())){
            throw new TipoUsuarioInvalidoException("Tipo de usuário inválido.");
        }
        Usuario usuarioEntity = UsuarioMapper.toEntity(usuario);

        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuarioEntity.setSenha(senhaCriptografada);

        Usuario createdUser = repository.save(usuarioEntity);
        return UsuarioMapper.toResponseDto(createdUser);
    }

    public UsuarioTokenDto autenticar(Usuario usuario) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuario.getEmail(), usuario.getSenha()
        );

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =
                repository.findByEmail(usuario.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email de usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.toUsuarioTokenDto(usuarioAutenticado, token);
    }

    public UsuarioResponseDto getUserById(Integer id) {
        Usuario usuarioFinded = repository.findById(id).orElseThrow(()-> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        return UsuarioMapper.toResponseDto(usuarioFinded);
    }

    public UsuarioUpdateTokenDto updateUserById(Integer id, Usuario usuario) {
        Usuario existingUser = repository.findById(id).orElseThrow(()-> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        Usuario updatedUser = UsuarioMapper.updateUserFields(existingUser, usuario);
        UsuarioResponseDto usuarioResponseDto = UsuarioMapper.toResponseDto(updatedUser);

        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        updatedUser.setSenha(senhaCriptografada);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                updatedUser.getEmail(), updatedUser.getSenha()
        );

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        repository.save(updatedUser);

        UsuarioUpdateTokenDto response = UsuarioMapper.toUsuarioUpdateDto(usuarioResponseDto, token);
        return response;
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
