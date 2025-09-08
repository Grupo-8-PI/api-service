package school.sptech.hub.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class UsuarioValidator {

    public static final Logger logger = LoggerFactory.getLogger(UsuarioValidator.class);

    public static final Set<String> TIPOS_VALIDOS = Set.of("ADMIN", "CLIENTE");

    public static boolean isValidUserType(String userType){
        if (userType == null || userType.trim().isEmpty()) {
            logger.warn("Tentativa de cadastro com tipo de usuário nulo ou vazio");
            return false;
        }

        String userTypeUpper = userType.trim().toUpperCase();
        boolean isValid = TIPOS_VALIDOS.contains(userTypeUpper);

        if (!isValid) {
            logger.warn("Tentativa de uso de tipo de usuário inválido: {}", userType);
        }

        return isValid;
    }
}
