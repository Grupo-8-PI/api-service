package school.sptech.hub.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import school.sptech.hub.application.service.AutenticacaoService;
import school.sptech.hub.domain.dto.usuario.UsuarioDetalhesDto;
import school.sptech.hub.domain.entity.Usuario;
import school.sptech.hub.application.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AutenticacaoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    private Usuario usuario;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("Fulano");
        usuario.setEmail("fulano@email.com");
        usuario.setSenha("123456");
        usuario.setTelefone("11999999999");
        usuario.setTipo_usuario("CLIENTE");
        usuario.setCpf("12345678901");
        usuario.setDtNascimento(LocalDate.of(2000, 1, 1));
    }

    @Test
    public void deveRetornarUsuarioQuandoEmailExiste() {

        when(usuarioRepository.findByEmail("fulano@email.com"))
                .thenReturn(Optional.of(usuario));

        var resultado = autenticacaoService.loadUserByUsername("fulano@email.com");

        assertNotNull(resultado);
        assertEquals("fulano@email.com", resultado.getUsername());
        assertInstanceOf(UsuarioDetalhesDto.class, resultado);
    }

    @Test
    public void deveLancarExcecaoQuandoEmailNaoExiste() {
        when(usuarioRepository.findByEmail("naoexiste@email.com"))
                .thenReturn(Optional.empty());

        UsuarioNaoEncontradoException excecao = assertThrows(
                UsuarioNaoEncontradoException.class,
                () -> autenticacaoService.loadUserByUsername("naoexiste@email.com")
        );

        assertEquals("Usuario: naoexiste@email.com não encontrado.", excecao.getMessage());
    }
}
