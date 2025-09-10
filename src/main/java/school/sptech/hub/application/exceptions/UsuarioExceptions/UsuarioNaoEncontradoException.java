package school.sptech.hub.application.exceptions.UsuarioExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsuarioNaoEncontradoException extends RuntimeException {

    private static final String MENSAGEM_PADRAO = "Usuário não encontrado.";

    public UsuarioNaoEncontradoException() {
        super(MENSAGEM_PADRAO);
    }

    public UsuarioNaoEncontradoException(String message) {
        super(message != null && !message.isBlank() ? message : MENSAGEM_PADRAO);
    }
}
