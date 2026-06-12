package cibertec.edu.pe.rabbit.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    public Queue colaPedidosFacturar() {
        // El primer parámetro es el nombre exacto de la cola
        // El segundo parámetro (true) es para que la cola sea durable (no se borre al reiniciar RabbitMQ)
        return new Queue("cola.pedidos.facturar", true);
    }
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
