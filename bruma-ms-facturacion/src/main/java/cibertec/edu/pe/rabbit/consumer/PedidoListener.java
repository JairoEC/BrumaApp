package cibertec.edu.pe.rabbit.consumer;

import cibertec.edu.pe.mapper.FacturaMapper;
import cibertec.edu.pe.model.Factura;
import cibertec.edu.pe.rabbit.dto.PedidoEventDto;
import cibertec.edu.pe.repository.FacturaRepository;
import cibertec.edu.pe.service.FacturaService;
import cibertec.edu.pe.service.impl.EmailFacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoListener {

    private final FacturaMapper facturaMapper;
    private final FacturaService facturaService;
    private final FacturaRepository facturaRepository;
    private final EmailFacturaService emailFacturaService;

    @RabbitListener(queues = "cola.pedidos.facturar")
    public void procesarPedidoRecibido(PedidoEventDto evento){
        Factura factura = facturaMapper.toEntity(evento);
        facturaService.crearFactura(factura);
        emailFacturaService.enviarComprobante("epa", factura);
    }
}
