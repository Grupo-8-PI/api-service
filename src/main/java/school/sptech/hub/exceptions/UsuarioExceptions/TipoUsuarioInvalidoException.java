package school.sptech.hub.exceptions.UsuarioExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TipoUsuarioInvalidoException extends RuntimeException{
    public TipoUsuarioInvalidoException(String message) {
        super(message);
    }

}
