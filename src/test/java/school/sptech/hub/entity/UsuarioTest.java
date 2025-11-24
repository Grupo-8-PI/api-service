package school.sptech.hub.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.*;
import school.sptech.hub.infraestructure.persistance.usuarioPersistance.UsuarioEntity;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioTest {

    private static Validator validator;
    private UsuarioEntity usuario;

    @BeforeAll
    public static void setupValidatorInstance() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @BeforeEach
    void setUp() {
        usuario = new UsuarioEntity();
    }

    @Test
    @DisplayName("Deve definir e obter ID corretamente")
    public void testGetAndSetId() {
        // Arrange & Act
        usuario.setId(1);

        // Assert
        assertEquals(1, usuario.getId());
    }

    @Test
    @DisplayName("Deve definir e obter nome corretamente")
    public void testGetAndSetNome() {
        // Arrange & Act
        usuario.setNome("João Silva");

        // Assert
        assertEquals("João Silva", usuario.getNome());
    }

    @Test
    @DisplayName("Deve definir e obter email corretamente")
    public void testGetAndSetEmail() {
        // Arrange & Act
        usuario.setEmail("joao@exemplo.com");

        // Assert
        assertEquals("joao@exemplo.com", usuario.getEmail());
    }

    @Test
    @DisplayName("Deve definir e obter telefone corretamente")
    public void testGetAndSetTelefone() {
        // Arrange & Act
        usuario.setTelefone("11999999999");

        // Assert
        assertEquals("11999999999", usuario.getTelefone());
    }

    @Test
    @DisplayName("Deve definir e obter tipo de usuário corretamente")
    public void testGetAndSetTipoUsuario() {
        // Arrange & Act
        usuario.setTipo_usuario("ADMIN");

        // Assert
        assertEquals("ADMIN", usuario.getTipo_usuario());
    }

    @Test
    @DisplayName("Deve definir e obter CPF corretamente")
    public void testGetAndSetCpf() {
        // Arrange & Act
        usuario.setCpf("12345678900");

        // Assert
        assertEquals("12345678900", usuario.getCpf());
    }

    @Test
    @DisplayName("Deve definir e obter senha corretamente")
    public void testSetAndGetSenha() {
        // Arrange & Act
        usuario.setSenha("senha123");

        // Assert
        assertEquals("senha123", usuario.getSenha());
    }

    @Test
    @DisplayName("Deve falhar na validação com CPF muito longo")
    public void testCpfValidationTooLong() {
        // Arrange
        usuario.setCpf(null); // CPF nulo - vai violar @NotNull
        usuario.setDtNascimento(LocalDate.of(1990, 1, 1));
        usuario.setNome("João");
        usuario.setEmail("joao@email.com");
        usuario.setTipo_usuario("CLIENTE");
        usuario.setSenha("senha123");

        // Act
        Set<ConstraintViolation<UsuarioEntity>> violations = validator.validate(usuario);

        // Assert
        assertFalse(violations.isEmpty(), "Deve haver violações de validação");

        boolean hasCpfViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("cpf"));

        assertTrue(hasCpfViolation, "Deve acusar CPF como obrigatório");
    }

    @Test
    @DisplayName("Deve falhar na validação com CPF muito curto")
    public void testCpfValidationTooShort() {
        // Arrange
        usuario.setCpf(null); // CPF nulo - vai violar @NotNull
        usuario.setDtNascimento(LocalDate.of(1990, 1, 1));
        usuario.setNome("João");
        usuario.setEmail("joao@email.com");
        usuario.setTipo_usuario("CLIENTE");
        usuario.setSenha("senha123");

        // Act
        Set<ConstraintViolation<UsuarioEntity>> violations = validator.validate(usuario);

        // Assert
        assertFalse(violations.isEmpty(), "Deve haver violações de validação");

        boolean hasCpfViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("cpf"));

        assertTrue(hasCpfViolation, "Deve acusar CPF como obrigatório");
    }

    @Test
    @DisplayName("Deve falhar na validação com data de nascimento nula")
    public void testDtNascimentoNotNull() {
        // Arrange
        usuario.setCpf("12345678900");
        usuario.setDtNascimento(null);
        usuario.setNome("João");
        usuario.setEmail("joao@email.com");
        usuario.setTipo_usuario("CLIENTE");
        usuario.setSenha("senha123");

        // Act
        Set<ConstraintViolation<UsuarioEntity>> violations = validator.validate(usuario);

        // Assert
        assertFalse(violations.isEmpty(), "Deve haver violações de validação");

        boolean hasDtNascimentoViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("dtNascimento"));

        assertTrue(hasDtNascimentoViolation, "Deve acusar dtNascimento como obrigatório");
    }

    @Test
    @DisplayName("Deve falhar na validação com email inválido")
    public void testEmailValidation() {
        // Arrange
        usuario.setEmail("email-invalido"); // Email sem formato válido
        usuario.setCpf("12345678900");
        usuario.setDtNascimento(LocalDate.of(1990, 1, 1));
        usuario.setNome("João");
        usuario.setTipo_usuario("CLIENTE");
        usuario.setSenha("senha123");

        // Act
        Set<ConstraintViolation<UsuarioEntity>> violations = validator.validate(usuario);

        // Assert
        assertFalse(violations.isEmpty(), "Deve haver violações de validação");

        boolean hasEmailViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email"));

        assertTrue(hasEmailViolation, "Deve acusar email inválido");
    }

    @Test
    @DisplayName("Deve falhar na validação com senha muito curta")
    public void testSenhaValidation() {
        // Arrange
        usuario.setSenha(null); // Senha nula - vai violar @NotNull
        usuario.setCpf("12345678900");
        usuario.setDtNascimento(LocalDate.of(1990, 1, 1));
        usuario.setNome("João");
        usuario.setEmail("joao@email.com");
        usuario.setTipo_usuario("CLIENTE");

        // Act
        Set<ConstraintViolation<UsuarioEntity>> violations = validator.validate(usuario);

        // Assert
        assertFalse(violations.isEmpty(), "Deve haver violações de validação");

        boolean hasSenhaViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("senha"));

        assertTrue(hasSenhaViolation, "Deve acusar senha como obrigatória");
    }

    @Test
    @DisplayName("Deve passar na validação com usuário válido")
    public void testValidUsuario() {
        // Arrange
        usuario.setId(1);
        usuario.setNome("João");
        usuario.setEmail("joao@email.com");
        usuario.setTelefone("11999999999");
        usuario.setTipo_usuario("CLIENTE");
        usuario.setCpf("12345678900"); // 11 dígitos - válido
        usuario.setSenha("senha123");
        usuario.setDtNascimento(LocalDate.of(1990, 1, 1));

        // Act
        Set<ConstraintViolation<UsuarioEntity>> violations = validator.validate(usuario);

        // Assert
        assertTrue(violations.isEmpty(), "Usuário válido não deve ter violações");
    }

    @Test
    @DisplayName("Deve verificar igualdade entre usuários")
    public void testEquality() {
        // Arrange
        UsuarioEntity usuario1 = new UsuarioEntity(1, "João", "joao@email.com", "11999999999", "CLIENTE", "12345678900", "senha123", LocalDate.of(1990, 1, 1));

        UsuarioEntity usuario2 = new UsuarioEntity(1, "João", "joao@email.com", "11999999999", "CLIENTE", "12345678900", "senha123", LocalDate.of(1990, 1, 1));

        UsuarioEntity usuario3 = new UsuarioEntity(2, "Maria", "maria@email.com", "11888888888", "ADMIN", "98765432100", "senha456", LocalDate.of(1985, 5, 15));

        // Act & Assert
        assertEquals(usuario1, usuario2, "Usuários com mesmos dados devem ser iguais");
        assertNotEquals(usuario1, usuario3, "Usuários com dados diferentes devem ser diferentes");
        assertEquals(usuario1.hashCode(), usuario2.hashCode(), "HashCodes devem ser iguais para objetos iguais");
    }
}
