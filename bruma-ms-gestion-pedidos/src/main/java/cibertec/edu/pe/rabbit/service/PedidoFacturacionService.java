package cibertec.edu.pe.rabbit.service;

import cibertec.edu.pe.entity.Cliente;
import cibertec.edu.pe.entity.DetallePedido;
import cibertec.edu.pe.entity.Pedido;
import cibertec.edu.pe.feignclient.client.AtencionFeignClient;
import cibertec.edu.pe.mapper.PedidoMapper;
import cibertec.edu.pe.rabbit.dto.DetalleEventDto;
import cibertec.edu.pe.rabbit.dto.PedidoEventDto;
import cibertec.edu.pe.rabbit.producer.PedidoProducer;
import cibertec.edu.pe.repository.ClienteRepository;
import cibertec.edu.pe.repository.DetallePedidoRepository;
import cibertec.edu.pe.repository.PedidoRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoFacturacionService {
    private final PedidoRepository pedidoRepository;
    private final PedidoProducer pedidoProducer;
    private final DetallePedidoRepository detallePedidoRepository;
    private final ClienteRepository clienteRepository;
    private final AtencionFeignClient atencionFeignClient;
    @Transactional
    public void enviarComprobante(Pedido pedido) {
        // Agrega aquí los montos/detalles necesarios que requiere tu FacturaMapper
        List<DetallePedido> detalles = detallePedidoRepository.findByPedidoId(pedido.getId());
        List<DetalleEventDto> detalleEventDtos = detalles.stream().map(
                det -> DetalleEventDto.builder()
                        .nombreProducto(det.getNombreProducto())
                        .productoId(det.getProductoId())
                        .cantidad(det.getCantidad())
                        .precioUnitario(det.getPrecioUnitario())
                        .subtotal(det.getSubtotal())
                        .build()
        ).toList();
        //
        Cliente cliente = clienteRepository.findById(pedido.getClienteId())
                .orElseThrow(()->new NotFoundException("Cliente no encontrado"));
        log.info("Correo: "+cliente.getEmail());
        // Construir el DTO que espera el evento
        PedidoEventDto evento = PedidoEventDto
                .builder()
                .pedidoId(pedido.getId())
                .clienteId(pedido.getClienteId())
                .clienteDni(cliente.getDni())
                .mesaId(pedido.getMesaId())
                .total(pedido.getTotal())
                .email("jespinoza96@gmail.com")
                .detalles(detalleEventDtos)
                .build();
        evento.setPedidoId(pedido.getId());
        //evento.setEmail(pedido.getEmail());

        // Despachar el mensaje asíncronamente hacia RabbitMQ
        pedidoProducer.enviarPedidoAFacturar(evento);
    }
}
