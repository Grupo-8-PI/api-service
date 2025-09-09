package school.sptech.hub.exceptions.UsuarioExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TipoUsuarioInvalidoException extends RuntimeException{

    private static final String MENSAGEM_PADRAO = "Tipo de usuário inválido. Tipos aceitos: ADMIN, CLIENTE.";

    public TipoUsuarioInvalidoException() {
        super(MENSAGEM_PADRAO);
    }

    public TipoUsuarioInvalidoException(String message) {
        super(message != null && !message.isBlank() ? message : MENSAGEM_PADRAO);
    }

}
