package school.sptech.hub.application.exceptions.CategoriaExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoriaNaoEncontradaException extends RuntimeException {

    private static final String MENSAGEM_PADRAO = "Categoria não encontrada.";

    public CategoriaNaoEncontradaException() {
        super(MENSAGEM_PADRAO);
    }

    public CategoriaNaoEncontradaException(String message) {
        super(message != null && !message.isBlank() ? message : MENSAGEM_PADRAO);
    }
}
