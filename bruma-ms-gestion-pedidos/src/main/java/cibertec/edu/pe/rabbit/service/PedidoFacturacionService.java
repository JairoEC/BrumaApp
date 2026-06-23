package cibertec.edu.pe.rabbit.service;

import cibertec.edu.pe.entity.Pedido;
import cibertec.edu.pe.rabbit.dto.PedidoEventDto;
import cibertec.edu.pe.rabbit.producer.PedidoProducer;
import cibertec.edu.pe.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PedidoFacturacionService {
    private final PedidoRepository pedidoRepository;
    private final PedidoProducer pedidoProducer;

    @Transactional
    public void completarPedido(Long pedidoId) {
        // ... Lógica para guardar o procesar el pedido localmente ...
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();

        // Construir el DTO que espera el evento
        PedidoEventDto evento = new PedidoEventDto();
        evento.setPedidoId(pedido.getId());
        //evento.setEmail(pedido.getEmail());
        // Agrega aquí los montos/detalles necesarios que requiere tu FacturaMapper

        // Despachar el mensaje asíncronamente hacia RabbitMQ
        pedidoProducer.enviarPedidoAFacturar(evento);
    }
}
