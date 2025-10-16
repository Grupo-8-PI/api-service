package school.sptech.hub.application.adapter.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import school.sptech.hub.application.adapter.ChatGptAdapter;

@Component
public class LivroProducerAdapter implements ChatGptAdapter {

    private final RabbitTemplate rabbitTemplate;

    @Value("${RABBITMQ_EXCHANGE:livros.exchange}")
    private String exchangeName;

    @Value("${RABBITMQ_ROUTING_KEY:livro.criado}")
    private String routingKey;

    public LivroProducerAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public String gerarSinopse(String titulo, String autor) {
        rabbitTemplate.convertAndSend(
                exchangeName,
                routingKey,
                titulo
        );
        System.out.println("[Adapter] Livro enviado -> " + titulo);
        return titulo;
    }
}
