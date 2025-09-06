package school.sptech.hub.application.exceptions.LivroExceptions;

public class LivroJaExisteException extends RuntimeException {
    public LivroJaExisteException(String message) {
        super(message);
    }
}
