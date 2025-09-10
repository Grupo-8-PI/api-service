package school.sptech.hub.exceptions.VendaExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VendaNaoEncontradaException extends RuntimeException {

    private static final String MENSAGEM_PADRAO = "Venda não encontrada.";

    public VendaNaoEncontradaException() {
        super(MENSAGEM_PADRAO);
    }

    public VendaNaoEncontradaException(String message) {
        super(message != null && !message.isBlank() ? message : MENSAGEM_PADRAO);
    }
}
