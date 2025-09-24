package school.sptech.hub.application.exceptions.AcabamentoExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AcabamentoNaoEncontradoException extends RuntimeException {

    private static final String MENSAGEM_PADRAO = "Acabamento não encontrado.";

    public AcabamentoNaoEncontradoException() {
        super(MENSAGEM_PADRAO);
    }

    public AcabamentoNaoEncontradoException(String message) {
        super(message != null && !message.isBlank() ? message : MENSAGEM_PADRAO);
    }
}
