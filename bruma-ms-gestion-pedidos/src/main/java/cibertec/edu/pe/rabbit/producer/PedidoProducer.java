package cibertec.edu.pe.rabbit.producer;

import cibertec.edu.pe.rabbit.config.RabbitProducerConfig;
import cibertec.edu.pe.rabbit.dto.PedidoEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoProducer {
    private final RabbitTemplate rabbitTemplate;

    public void enviarPedidoAFacturar(PedidoEventDto evento) {
        log.info("Enviando pedido ID: {} a la cola de facturación para el email: {}",
                evento.getPedidoId(), evento.getEmail());
        log.info(evento.toString());
        // Enviamos el objeto directo al Exchange usando la Routing Key
        rabbitTemplate.convertAndSend(
                RabbitProducerConfig.EXCHANGE_NAME,
                RabbitProducerConfig.ROUTING_KEY,
                evento
        );
    }
}
