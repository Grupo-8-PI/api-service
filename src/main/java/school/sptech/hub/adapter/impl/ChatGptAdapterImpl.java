package school.sptech.hub.adapter.impl;

import org.springframework.stereotype.Component;
import school.sptech.hub.adapter.ChatGptAdapter;

@Component
public class ChatGptAdapterImpl implements ChatGptAdapter {
    public String gerarSinopse(String titulo, String autor) {
        // Implementação fictícia para simular a geração de sinopse
        // TODO : Integrar com a API real do ChatGPT ou outro serviço de IA
        return "Sinopse gerada para o livro '" + titulo + "' de " + autor + ".";
    }
}
