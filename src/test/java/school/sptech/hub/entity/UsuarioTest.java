package school.sptech.hub.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.*;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testGetAndSetId() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        assertEquals(1, usuario.getId());
    }

    @Test
    public void testGetAndSetNome() {
        Usuario usuario = new Usuario();
        usuario.setNome("João Silva");
        assertEquals("João Silva", usuario.getNome());
    }

    @Test
    public void testGetAndSetEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("joao@exemplo.com");
        assertEquals("joao@exemplo.com", usuario.getEmail());
    }

    @Test
    public void testGetAndSetTelefone() {
        Usuario usuario = new Usuario();
        usuario.setTelefone("11999999999");
        assertEquals("11999999999", usuario.getTelefone());
    }

    @Test
    public void testGetAndSetTipoUsuario() {
        Usuario usuario = new Usuario();
        usuario.setTipo_usuario("ADMIN");
        assertEquals("ADMIN", usuario.getTipo_usuario());
    }

    @Test
    public void testGetAndSetCpf() {
        Usuario usuario = new Usuario();
        usuario.setCpf("12345678900");
        assertEquals("12345678900", usuario.getCpf());
    }

    @Test
    public void testCpfValidationTooLong() {
        Usuario usuario = new Usuario();
        usuario.setCpf("123456789012345");
        usuario.setDtNascimento(LocalDate.of(1990, 1, 1));

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertFalse(violations.isEmpty());

        boolean hasCpfViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("cpf") && v.getMessage().contains("Invalid CPF"));

        assertTrue(hasCpfViolation, "Deve acusar CPF inválido");
    }

    @Test
    public void testDtNascimentoNotNull() {
        Usuario usuario = new Usuario();
        usuario.setCpf("12345678900");
        usuario.setDtNascimento(null);

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertFalse(violations.isEmpty());

        boolean hasDtNascimentoViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("dtNascimento"));

        assertTrue(hasDtNascimentoViolation, "Deve acusar dtNascimento como obrigatório");
    }

    @Test
    public void testValidUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("João");
        usuario.setEmail("joao@email.com");
        usuario.setTelefone("11999999999");
        usuario.setTipo_usuario("CLIENTE");
        usuario.setCpf("12345678900");
        usuario.setSenha("senha123");
        usuario.setDtNascimento(LocalDate.of(1990, 1, 1));

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertTrue(violations.isEmpty(), "Usuário válido não deve ter violações");
    }

    @Test
    public void testSetAndGetSenha() {
        Usuario usuario = new Usuario();
        usuario.setSenha("senha123");
        assertEquals("senha123", usuario.getSenha());
    }
}
