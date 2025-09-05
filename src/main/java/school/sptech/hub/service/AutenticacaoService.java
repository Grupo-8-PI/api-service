package school.sptech.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.hub.controller.dto.usuario.UsuarioDetalhesDto;
import school.sptech.hub.entity.Usuario;
import school.sptech.hub.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.repository.UsuarioRepository;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        String sanitizedUsername = username == null ? "" : username.trim().toLowerCase();

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(sanitizedUsername);

        if (usuarioOpt.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Credenciais inválidas");
        }

        Usuario usuario = usuarioOpt.get();

        return new UsuarioDetalhesDto(usuario);
    }
}
