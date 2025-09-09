package school.sptech.hub.exceptions.VendaExceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VendaInvalidaException extends RuntimeException{

    private static final String MENSAGEM_PADRAO = "Venda ou reserva inválida. Verifique os dados e tente novamente.";

    public VendaInvalidaException() {
        super(MENSAGEM_PADRAO);
    }

    public VendaInvalidaException(String message) {
        super(message != null && !message.isBlank() ? message : MENSAGEM_PADRAO);
    }
}
