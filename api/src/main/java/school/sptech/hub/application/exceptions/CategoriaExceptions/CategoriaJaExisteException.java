package school.sptech.hub.application.exceptions.CategoriaExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CategoriaJaExisteException extends RuntimeException {

    private static final String MENSAGEM_PADRAO = "Categoria já existe.";

    public CategoriaJaExisteException() {
        super(MENSAGEM_PADRAO);
    }

    public CategoriaJaExisteException(String message) {
        super(message != null && !message.isBlank() ? message : MENSAGEM_PADRAO);
    }
}
