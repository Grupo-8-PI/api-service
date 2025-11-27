package school.sptech.hub.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.hub.application.service.AutenticacaoService;
import school.sptech.hub.domain.dto.usuario.UsuarioDetalhesDto;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioEntity;
import school.sptech.hub.application.exceptions.UsuarioExceptions.UsuarioNaoEncontradoException;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AutenticacaoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    private UsuarioEntity usuarioEntity;

    @BeforeEach
    public void setup() {
        usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1);
        usuarioEntity.setNome("Fulano");
        usuarioEntity.setEmail("fulano@email.com");
        usuarioEntity.setSenha("123456");
        usuarioEntity.setTelefone("11999999999");
        usuarioEntity.setTipo_usuario("CLIENTE");
        usuarioEntity.setCpf("12345678901");
        usuarioEntity.setDtNascimento(LocalDate.of(2000, 1, 1));
    }

    @Test
    @DisplayName("Deve retornar usuário quando email existe")
    public void deveRetornarUsuarioQuandoEmailExiste() {
        // Arrange
        when(usuarioRepository.findByEmail("fulano@email.com"))
                .thenReturn(Optional.of(usuarioEntity));

        // Act
        var resultado = autenticacaoService.loadUserByUsername("fulano@email.com");

        // Assert
        assertNotNull(resultado);
        assertEquals("fulano@email.com", resultado.getUsername());
        assertInstanceOf(UsuarioDetalhesDto.class, resultado);
        verify(usuarioRepository, times(1)).findByEmail("fulano@email.com");
    }

    @Test
    @DisplayName("Deve lançar exceção quando email não existe")
    public void deveLancarExcecaoQuandoEmailNaoExiste() {
        // Arrange
        when(usuarioRepository.findByEmail("naoexiste@email.com"))
                .thenReturn(Optional.empty());

        // Act & Assert
        UsuarioNaoEncontradoException excecao = assertThrows(
                UsuarioNaoEncontradoException.class,
                () -> autenticacaoService.loadUserByUsername("naoexiste@email.com")
        );

        assertEquals("Usuario: naoexiste@email.com não encontrado.", excecao.getMessage());
        verify(usuarioRepository, times(1)).findByEmail("naoexiste@email.com");
    }

    @Test
    @DisplayName("Deve verificar se o UsuarioDetalhesDto contém as informações corretas")
    public void deveVerificarSeUsuarioDetalhesDtoContemInformacoesCorretas() {
        // Arrange
        when(usuarioRepository.findByEmail("fulano@email.com"))
                .thenReturn(Optional.of(usuarioEntity));

        // Act
        var resultado = (UsuarioDetalhesDto) autenticacaoService.loadUserByUsername("fulano@email.com");

        // Assert
        assertNotNull(resultado);
        assertEquals("fulano@email.com", resultado.getUsername());
        assertEquals("123456", resultado.getPassword());
        assertTrue(resultado.isEnabled());
        assertTrue(resultado.isAccountNonExpired());
        assertTrue(resultado.isAccountNonLocked());
        assertTrue(resultado.isCredentialsNonExpired());
        verify(usuarioRepository, times(1)).findByEmail("fulano@email.com");
    }
}
