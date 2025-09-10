package school.sptech.hub.application.validators;

public class UsuarioValidator {
    public static boolean isValidUserType(String userType) {
        return "admin".equalsIgnoreCase(userType) || "cliente".equalsIgnoreCase(userType);
    }
}
