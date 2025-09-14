package school.sptech.hub.application.exceptions.VendaExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VendaInvalidaException extends RuntimeException {

    private static final String MENSAGEM_PADRAO = "Venda inválida.";

    public VendaInvalidaException() {
        super(MENSAGEM_PADRAO);
    }

    public VendaInvalidaException(String message) {
        super(message != null && !message.isBlank() ? message : MENSAGEM_PADRAO);
    }
}
