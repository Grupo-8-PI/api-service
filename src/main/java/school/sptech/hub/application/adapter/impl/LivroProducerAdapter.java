package school.sptech.hub.application.adapter.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import school.sptech.hub.application.adapter.ChatGptAdapter;
import school.sptech.hub.domain.dto.livro.LivroCriadoEventDto;
import school.sptech.hub.domain.entity.Livro;

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
        // Método antigo mantido para compatibilidade
        LivroCriadoEventDto evento = new LivroCriadoEventDto();
        evento.setTitulo(titulo);
        evento.setAutor(autor);

        enviarEvento(evento);
        return titulo;
    }

    @Override
    public String gerarSinopse(Livro livro) {
        // Novo método: cria o DTO com TODOS os dados do livro
        LivroCriadoEventDto evento = new LivroCriadoEventDto();
        evento.setLivroId(livro.getId());
        evento.setTitulo(livro.getTitulo());
        evento.setAutor(livro.getAutor());
        evento.setIsbn(livro.getIsbn());

        enviarEvento(evento);
        return livro.getTitulo();
    }

    // Método privado para evitar duplicação
    private void enviarEvento(LivroCriadoEventDto evento) {
        rabbitTemplate.convertAndSend(
                exchangeName,
                routingKey,
                evento
        );

        System.out.println("[Adapter] Evento de livro criado enviado -> " + evento);
    }
}
