package school.sptech.hub.application.exceptions.LivroExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LivroNaoEncontradoException extends RuntimeException {

    private static final String MENSAGEM_PADRAO = "Livro não encontrado.";

    public LivroNaoEncontradoException() {
        super(MENSAGEM_PADRAO);
    }

    public LivroNaoEncontradoException(String message) {
        super(message != null && !message.isBlank() ? message : MENSAGEM_PADRAO);
    }
}
