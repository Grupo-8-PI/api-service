package school.sptech.hub.application.adapter.impl;

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
        try {
            rabbitTemplate.convertAndSend(
                    exchangeName,
                    routingKey,
                    evento
            );
            System.out.println("[Adapter] Evento de livro criado enviado -> " + evento);
        } catch(Exception e){
            System.out.println("Erro ao enviar evento: " + e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
