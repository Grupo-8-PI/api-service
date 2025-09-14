package school.sptech.hub.application.exceptions.AcabamentoExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AcabamentoJaExisteException extends RuntimeException {

    private static final String MENSAGEM_PADRAO = "Acabamento já existe.";

    public AcabamentoJaExisteException() {
        super(MENSAGEM_PADRAO);
    }

    public AcabamentoJaExisteException(String message) {
        super(message != null && !message.isBlank() ? message : MENSAGEM_PADRAO);
    }
}
