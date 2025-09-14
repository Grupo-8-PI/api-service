package school.sptech.hub.application.exceptions.LivroExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class LivroJaExisteException extends RuntimeException {

    private static final String MENSAGEM_PADRAO = "Livro já existe.";

    public LivroJaExisteException() {
        super(MENSAGEM_PADRAO);
    }

    public LivroJaExisteException(String message) {
        super(message != null && !message.isBlank() ? message : MENSAGEM_PADRAO);
    }
}
