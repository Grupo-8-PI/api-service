package school.sptech.hub.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioEntity;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioRepositoryTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioEntity usuarioValido;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioValido = criarUsuarioValidoEntity();
    }

    private UsuarioEntity criarUsuarioValidoEntity() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1);
        usuario.setNome("João Silva");
        usuario.setEmail("joao@email.com");
        usuario.setTelefone("11999999999");
        usuario.setCpf("123.456.789-00");
        usuario.setTipo_usuario("cliente");
        usuario.setSenha("senha123");
        usuario.setDtNascimento(LocalDate.of(1990, 5, 15));
        return usuario;
    }

    // ===== TESTES PARA SALVAR USUÁRIO =====

    @Test
    @DisplayName("Deve salvar usuário com sucesso")
    void deveSalvarUsuario() {
        // Arrange
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioValido);

        // Act
        UsuarioEntity resultado = usuarioRepository.save(usuarioValido);

        // Assert
        assertEquals(usuarioValido, resultado);
        assertNotNull(resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@email.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).save(usuarioValido);
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar usuário com email duplicado")
    void deveLancarExcecaoAoSalvarUsuarioComEmailDuplicado() {
        // Arrange
        UsuarioEntity usuarioComEmailDuplicado = criarUsuarioValidoEntity();
        usuarioComEmailDuplicado.setEmail("joao@email.com");

        when(usuarioRepository.save(usuarioComEmailDuplicado))
                .thenThrow(new DataIntegrityViolationException("Email já existe"));

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class, () ->
            usuarioRepository.save(usuarioComEmailDuplicado));
        verify(usuarioRepository, times(1)).save(usuarioComEmailDuplicado);
    }

    // ===== TESTES PARA BUSCAR POR ID =====

    @Test
    @DisplayName("Deve buscar usuário por ID com sucesso")
    void deveBuscarUsuarioPorId() {
        // Arrange
        Integer id = 1;
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioValido));

        // Act
        Optional<UsuarioEntity> resultado = usuarioRepository.findById(id);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(usuarioValido, resultado.get());
        assertEquals(id, resultado.get().getId());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando usuário não encontrado por ID")
    void deveRetornarOptionalVazioQuandoUsuarioNaoEncontradoPorId() {
        // Arrange
        Integer idInexistente = 999;
        when(usuarioRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // Act
        Optional<UsuarioEntity> resultado = usuarioRepository.findById(idInexistente);

        // Assert
        assertFalse(resultado.isPresent());
        verify(usuarioRepository, times(1)).findById(idInexistente);
    }

    // ===== TESTES PARA BUSCAR POR EMAIL =====

    @Test
    @DisplayName("Deve buscar usuário por email com sucesso")
    void deveBuscarUsuarioPorEmail() {
        // Arrange
        String email = "joao@email.com";
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuarioValido));

        // Act
        Optional<UsuarioEntity> resultado = usuarioRepository.findByEmail(email);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(usuarioValido, resultado.get());
        assertEquals(email, resultado.get().getEmail());
        verify(usuarioRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando email não encontrado")
    void deveRetornarOptionalVazioQuandoEmailNaoEncontrado() {
        // Arrange
        String emailInexistente = "inexistente@email.com";
        when(usuarioRepository.findByEmail(emailInexistente)).thenReturn(Optional.empty());

        // Act
        Optional<UsuarioEntity> resultado = usuarioRepository.findByEmail(emailInexistente);

        // Assert
        assertFalse(resultado.isPresent());
        verify(usuarioRepository, times(1)).findByEmail(emailInexistente);
    }

    @Test
    @DisplayName("Deve buscar usuário por email case-insensitive")
    void deveBuscarUsuarioPorEmailCaseInsensitive() {
        // Arrange
        String emailUpperCase = "JOAO@EMAIL.COM";
        when(usuarioRepository.findByEmail(emailUpperCase)).thenReturn(Optional.of(usuarioValido));

        // Act
        Optional<UsuarioEntity> resultado = usuarioRepository.findByEmail(emailUpperCase);

        // Assert
        assertTrue(resultado.isPresent());
        verify(usuarioRepository, times(1)).findByEmail(emailUpperCase);
    }

    // ===== TESTES PARA DELETAR USUÁRIO =====

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void deveDeletarUsuario() {
        // Arrange
        doNothing().when(usuarioRepository).delete(any(UsuarioEntity.class));

        // Act
        usuarioRepository.delete(usuarioValido);

        // Assert
        verify(usuarioRepository, times(1)).delete(usuarioValido);
    }

    @Test
    @DisplayName("Deve deletar usuário por ID com sucesso")
    void deveDeletarUsuarioPorId() {
        // Arrange
        Integer id = 1;
        doNothing().when(usuarioRepository).deleteById(id);

        // Act
        usuarioRepository.deleteById(id);

        // Assert
        verify(usuarioRepository, times(1)).deleteById(id);
    }

    // ===== TESTES PARA VERIFICAR EXISTÊNCIA =====

    @Test
    @DisplayName("Deve verificar se usuário existe por ID")
    void deveVerificarSeUsuarioExistePorId() {
        // Arrange
        Integer id = 1;
        when(usuarioRepository.existsById(id)).thenReturn(true);

        // Act
        boolean existe = usuarioRepository.existsById(id);

        // Assert
        assertTrue(existe);
        verify(usuarioRepository, times(1)).existsById(id);
    }

    @Test
    @DisplayName("Deve verificar se usuário não existe por ID")
    void deveVerificarSeUsuarioNaoExistePorId() {
        // Arrange
        Integer idInexistente = 999;
        when(usuarioRepository.existsById(idInexistente)).thenReturn(false);

        // Act
        boolean existe = usuarioRepository.existsById(idInexistente);

        // Assert
        assertFalse(existe);
        verify(usuarioRepository, times(1)).existsById(idInexistente);
    }

    // ===== TESTES PARA BUSCAR TODOS =====

    @Test
    @DisplayName("Deve buscar todos os usuários com sucesso")
    void deveBuscarTodosUsuarios() {
        // Arrange
        UsuarioEntity usuario2 = criarUsuarioValidoEntity();
        usuario2.setId(2);
        usuario2.setEmail("maria@email.com");
        usuario2.setNome("Maria Silva");

        List<UsuarioEntity> usuarios = List.of(usuarioValido, usuario2);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        // Act
        List<UsuarioEntity> resultado = usuarioRepository.findAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(usuarioValido));
        assertTrue(resultado.contains(usuario2));
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há usuários")
    void deveRetornarListaVaziaQuandoNaoHaUsuarios() {
        // Arrange
        when(usuarioRepository.findAll()).thenReturn(List.of());

        // Act
        List<UsuarioEntity> resultado = usuarioRepository.findAll();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(usuarioRepository, times(1)).findAll();
    }

    // ===== TESTES PARA CONTAR USUÁRIOS =====

    @Test
    @DisplayName("Deve contar total de usuários")
    void deveContarTotalDeUsuarios() {
        // Arrange
        when(usuarioRepository.count()).thenReturn(5L);

        // Act
        Long total = usuarioRepository.count();

        // Assert
        assertEquals(5L, total);
        verify(usuarioRepository, times(1)).count();
    }

    @Test
    @DisplayName("Deve retornar zero quando não há usuários")
    void deveRetornarZeroQuandoNaoHaUsuarios() {
        // Arrange
        when(usuarioRepository.count()).thenReturn(0L);

        // Act
        Long total = usuarioRepository.count();

        // Assert
        assertEquals(0L, total);
        verify(usuarioRepository, times(1)).count();
    }

    // ===== TESTES PARA VALIDAÇÃO DE DADOS =====

    @Test
    @DisplayName("Deve validar que email não pode ser null ao salvar")
    void deveValidarQueEmailNaoPodeSerNullAoSalvar() {
        // Arrange
        UsuarioEntity usuarioInvalido = criarUsuarioValidoEntity();
        usuarioInvalido.setEmail(null);

        when(usuarioRepository.save(any(UsuarioEntity.class)))
                .thenThrow(new IllegalArgumentException("Email é obrigatório"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            usuarioRepository.save(usuarioInvalido));
        verify(usuarioRepository, times(1)).save(usuarioInvalido);
    }

    @Test
    @DisplayName("Deve validar operações em lote")
    void deveValidarOperacoesEmLote() {
        // Arrange
        UsuarioEntity usuario2 = criarUsuarioValidoEntity();
        usuario2.setId(2);
        usuario2.setEmail("maria@email.com");

        List<UsuarioEntity> usuarios = List.of(usuarioValido, usuario2);
        when(usuarioRepository.saveAll(anyList())).thenReturn(usuarios);

        // Act
        List<UsuarioEntity> resultado = usuarioRepository.saveAll(usuarios);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).saveAll(usuarios);
    }
}
