package cibertec.edu.pe.rabbit.service;

import cibertec.edu.pe.entity.Pedido;
import cibertec.edu.pe.mapper.PedidoMapper;
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
    private final PedidoMapper pedidoMapper;
    @Transactional
    public void enviarComprobante(Long pedidoId) {
        // ... Lógica para guardar o procesar el pedido localmente ...
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();

        // Construir el DTO que espera el evento
        PedidoEventDto evento = PedidoEventDto
                .builder()
                .pedidoId(pedido.getId())
                .clienteId(pedido.getClienteId())
                .clienteDni("111111")
                .mesaId(pedido.getMesaId())
                .total(pedido.getTotal())
                .email("jespinoza96@gmail.com")
                .build();
        evento.setPedidoId(pedido.getId());
        //evento.setEmail(pedido.getEmail());
        // Agrega aquí los montos/detalles necesarios que requiere tu FacturaMapper

        // Despachar el mensaje asíncronamente hacia RabbitMQ
        pedidoProducer.enviarPedidoAFacturar(evento);
    }
}
