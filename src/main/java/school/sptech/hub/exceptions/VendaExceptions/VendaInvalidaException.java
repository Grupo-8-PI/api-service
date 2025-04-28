package school.sptech.hub.exceptions.VendaExceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VendaInvalidaException extends RuntimeException{
    public VendaInvalidaException(String message) {
        super(message);
    }
}
