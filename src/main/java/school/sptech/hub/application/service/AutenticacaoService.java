package school.sptech.hub.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import school.sptech.hub.domain.dto.usuario.UsuarioDetalhesDto;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.application.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioRepository;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioEntity;
import school.sptech.hub.infraestructure.gateways.usuario.UsuarioEntityMapper;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UsuarioEntity> usuarioEntityOpt = usuarioRepository.findByEmail(username);

        if (usuarioEntityOpt.isEmpty()) {
            throw new UsuarioNaoEncontradoException(String.format("Usuario: %s não encontrado.", username));
        }

        Usuario usuario = UsuarioEntityMapper.toDomain(usuarioEntityOpt.get());
        return new UsuarioDetalhesDto(usuario);
    }
}
