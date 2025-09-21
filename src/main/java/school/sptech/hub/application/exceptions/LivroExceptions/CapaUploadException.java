package school.sptech.hub.application.exceptions.LivroExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CapaUploadException extends RuntimeException {

    private static final String MENSAGEM_PADRAO = "Erro ao fazer upload da capa do livro.";

    public CapaUploadException() {
        super(MENSAGEM_PADRAO);
    }

    public CapaUploadException(String message) {
        super(message != null && !message.isBlank() ? message : MENSAGEM_PADRAO);
    }
}
