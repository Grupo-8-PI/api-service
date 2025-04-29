package school.sptech.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import school.sptech.hub.controller.dto.UsuarioDetalhesDto;
import school.sptech.hub.entity.Usuario;
import school.sptech.hub.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.repository.UsuarioRepository;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(username);

        if (usuarioOpt.isEmpty()) {
            throw new UsuarioNaoEncontradoException(String.format("Usuario: %s não encontrado.", username));
        }

        return new UsuarioDetalhesDto(usuarioOpt.get());
    }
}
