package cibertec.edu.pe.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitProducerConfig {
    public static final String QUEUE_NAME = "cola.pedidos.facturar";
    public static final String EXCHANGE_NAME = "exchange.pedidos";
    public static final String ROUTING_KEY = "routing.pedidos.facturar";

    // 1. Configurar el convertidor de JSON (Crucial para enviar DTOs)
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // 2. Definir la cola
    @Bean
    public Queue pedidosQueue() {
        return new Queue(QUEUE_NAME, true); // true = durable
    }

    // 3. Definir el Exchange (Direct o Topic según tu preferencia)
    @Bean
    public DirectExchange pedidosExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // 4. Enlazar la cola con el Exchange usando la clave de enrutamiento
    @Bean
    public Binding bindingPedidos(Queue pedidosQueue, DirectExchange pedidosExchange) {
        return BindingBuilder.bind(pedidosQueue).to(pedidosExchange).with(ROUTING_KEY);
    }
}
