package school.sptech.hub.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


class UsuarioValidatorTest {
    @Autowired
    private UsuarioValidator usuarioValidator;

    @Test
    @DisplayName("Quando isValidUserType acionado com admin ou cliente deve retornar true")
    void when_thrown_valid_user_type_of_user_should_return_true(){
        //ARRANGE
        String adminUserType = "admin";
        String clienteUserType = "cliente";

        //ACT
        Boolean resultWithAdmin = usuarioValidator.isValidUserType(adminUserType);
        Boolean resultWithCliente = usuarioValidator.isValidUserType(clienteUserType);
        //ASSERT
        assertTrue(resultWithAdmin);
        assertTrue(resultWithCliente);
    }
    @Test
    @DisplayName("Quando isValidUserType acionado com admin ou cliente deve retornar true")
    void when_thrown_invalid_user_type_of_user_should_return_false(){
        //ARRANGE
        String adminUserType = "admin123";
        String clienteUserType = "cliente123";

        //ACT
        Boolean resultWithAdmin = usuarioValidator.isValidUserType(adminUserType);
        Boolean resultWithCliente = usuarioValidator.isValidUserType(clienteUserType);
        //ASSERT
        assertFalse(resultWithAdmin);
        assertFalse(resultWithCliente);

    }
}
