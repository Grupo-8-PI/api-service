package school.sptech.hub.application.adapter.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import school.sptech.hub.application.adapter.ChatGptAdapter;
import school.sptech.hub.domain.dto.livro.LivroCriadoEventDto;
import school.sptech.hub.domain.entity.Livro;

import java.util.Arrays;

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
    public String gerarSinopse(Livro livro) {
        LivroCriadoEventDto evento = new LivroCriadoEventDto();
        evento.setLivroId(livro.getId());
        evento.setTitulo(livro.getTitulo());
        evento.setAutor(livro.getAutor());
        evento.setIsbn(livro.getIsbn());

        enviarEvento(evento);
        return livro.getTitulo();
    }

    private void enviarEvento(LivroCriadoEventDto evento) {
        ObjectMapper objectConverter = new ObjectMapper();
        String json = "";
        try {
            json = objectConverter.writeValueAsString(evento);
        } catch (JsonProcessingException e) {
            System.out.println("Houve um erro ao converter o evento para JSON: " + e.getMessage());
        }
        rabbitTemplate.convertAndSend(
                exchangeName,
                routingKey,
                json
        );
        System.out.println("[Adapter] Evento de livro criado enviado -> " + evento);
    }
}
