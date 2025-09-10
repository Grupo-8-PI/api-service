package school.sptech.hub.application.exceptions.ConservacaoExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ConservacaoNaoEncontradaException extends RuntimeException {

    private static final String MENSAGEM_PADRAO = "Conservação não encontrada.";

    public ConservacaoNaoEncontradaException() {
        super(MENSAGEM_PADRAO);
    }

    public ConservacaoNaoEncontradaException(String message) {
        super(message != null && !message.isBlank() ? message : MENSAGEM_PADRAO);
    }
}
