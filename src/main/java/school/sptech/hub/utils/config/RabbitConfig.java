package school.sptech.hub.utils.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${RABBITMQ_EXCHANGE:livros.exchange}")
    private String exchangeName;

    @Value("${RABBITMQ_QUEUE:livros.criados}")
    private String queueName;

    @Value("${RABBITMQ_ROUTING_KEY:livro.criado}")
    private String routingKey;

    @Bean
    public TopicExchange exchange() { return new TopicExchange(exchangeName); }

    @Bean
    public Queue queue() { return new Queue(queueName, true); }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
}
