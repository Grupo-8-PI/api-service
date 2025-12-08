package school.sptech.hub.application.usecases.usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.hub.application.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.application.gateways.usuario.UsuarioGateway;
import school.sptech.hub.domain.dto.usuario.UsuarioResponseDto;
import school.sptech.hub.domain.entity.Usuario;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCurrentUserUseCaseTest {

    @Mock
    private UsuarioGateway gateway;

    @InjectMocks
    private GetCurrentUserUseCase useCase;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("João Silva");
        usuario.setEmail("joao.silva@example.com");
        usuario.setTelefone("11999999999");
        usuario.setTipo_usuario("CLIENTE");
        usuario.setCpf("12345678900");
        usuario.setDtNascimento(LocalDate.of(1990, 1, 1));
    }

    @Test
    void execute_WhenUserExists_ShouldReturnUserResponse() {
        when(gateway.findById(1)).thenReturn(Optional.of(usuario));

        UsuarioResponseDto result = useCase.execute(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("João Silva", result.getNome());
        assertEquals("joao.silva@example.com", result.getEmail());
        assertEquals("cliente", result.getTipo_usuario());
        verify(gateway).findById(1);
    }

    @Test
    void execute_WhenAdminUserExists_ShouldReturnUserWithLowercaseRole() {
        usuario.setTipo_usuario("ADMIN");
        when(gateway.findById(1)).thenReturn(Optional.of(usuario));

        UsuarioResponseDto result = useCase.execute(1);

        assertNotNull(result);
        assertEquals("admin", result.getTipo_usuario());
        verify(gateway).findById(1);
    }

    @Test
    void execute_WhenUserDoesNotExist_ShouldThrowUsuarioNaoEncontradoException() {
        when(gateway.findById(999)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> useCase.execute(999));
        verify(gateway).findById(999);
    }

    @Test
    void execute_WhenUserHasNullTipoUsuario_ShouldReturnNull() {
        usuario.setTipo_usuario(null);
        when(gateway.findById(1)).thenReturn(Optional.of(usuario));

        UsuarioResponseDto result = useCase.execute(1);

        assertNotNull(result);
        assertNull(result.getTipo_usuario());
        verify(gateway).findById(1);
    }
}

