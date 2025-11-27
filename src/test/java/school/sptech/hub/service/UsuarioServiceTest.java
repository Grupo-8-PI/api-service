package school.sptech.hub.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.hub.utils.config.GerenciadorTokenJwt;
import school.sptech.hub.domain.dto.usuario.UsuarioResponseDto;
import school.sptech.hub.domain.dto.usuario.UsuarioTokenDto;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.application.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("Teste");
        usuario.setEmail("teste@email.com");
        usuario.setTelefone("123456789");
        usuario.setCpf("123.456.789-00");
        usuario.setTipo_usuario("comum");
        usuario.setSenha("senha123");
        usuario.setDtNascimento(LocalDate.of(2000, 1, 1));
    }

    @Test
    void deveBuscarUsuarioPorIdComSucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(usuario));

        UsuarioResponseDto result = usuarioService.getUserById(1);

        assertNotNull(result);
        assertEquals(usuario.getEmail(), result.getEmail());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoPorId() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.getUserById(1));
    }

    @Test
    void deveAutenticarUsuarioComSucesso() {
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(repository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));
        when(gerenciadorTokenJwt.generateToken(any())).thenReturn("token-fake");

        UsuarioTokenDto tokenDto = usuarioService.autenticar(usuario);

        assertNotNull(tokenDto);
        assertEquals("token-fake", tokenDto.getToken());
    }

    @Test
    void deveLancarExcecaoAoAutenticarUsuarioInexistente() {
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));
        when(repository.findByEmail(usuario.getEmail())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> usuarioService.autenticar(usuario));
    }

    @Test
    void deveDeletarUsuarioComSucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(usuario));

        Usuario deletado = usuarioService.deleteUserById(1);

        verify(repository).delete(usuario);
        assertEquals(usuario, deletado);
    }

    @Test
    void deveLancarExcecaoAoDeletarUsuarioInexistente() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.deleteUserById(1));
    }
}