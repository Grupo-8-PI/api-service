package school.sptech.hub.repository;

import org.junit.jupiter.api.Test;
import school.sptech.hub.entity.Usuario;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioRepositoryTest {

    UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);

    private Usuario criarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("Fulano");
        usuario.setEmail("fulano@email.com");
        // configure outros campos se precisar
        return usuario;
    }

    @Test
    void deveSalvarUsuario() {
        Usuario usuario = criarUsuario();
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioRepository.save(usuario);

        assertEquals(usuario, resultado);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void deveBuscarUsuarioPorId() {
        Usuario usuario = criarUsuario();
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioRepository.findById(usuario.getId());

        assertTrue(resultado.isPresent());
        assertEquals(usuario, resultado.get());
        verify(usuarioRepository).findById(usuario.getId());
    }

    @Test
    void deveBuscarUsuarioPorEmail() {
        Usuario usuario = criarUsuario();
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioRepository.findByEmail(usuario.getEmail());

        assertTrue(resultado.isPresent());
        assertEquals(usuario, resultado.get());
        verify(usuarioRepository).findByEmail(usuario.getEmail());
    }

    @Test
    void deveDeletarUsuario() {
        Usuario usuario = criarUsuario();

        doNothing().when(usuarioRepository).delete(usuario);

        usuarioRepository.delete(usuario);

        verify(usuarioRepository).delete(usuario);
    }
}
