package cibertec.edu.pe.rabbit.consumer;

import cibertec.edu.pe.mapper.FacturaMapper;
import cibertec.edu.pe.model.ComprobanteEmail;
import cibertec.edu.pe.model.Factura;
import cibertec.edu.pe.rabbit.dto.PedidoEventDto;
import cibertec.edu.pe.repository.ComprobanteEmailRepository;
import cibertec.edu.pe.repository.FacturaRepository;
import cibertec.edu.pe.service.FacturaService;
import cibertec.edu.pe.service.impl.ComprobanteEmailService;
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
    private final ComprobanteEmailService comprobanteEmailService;

    @RabbitListener(queues = "cola.pedidos.facturar")
    public void procesarPedidoRecibido(PedidoEventDto evento){
        Factura factura = facturaMapper.toEntity(evento);
        if(!facturaRepository.existsById(evento.getPedidoId())){
            facturaService.crearFactura(factura);
        }

        ComprobanteEmail comprobante = comprobanteEmailService.obtenerOCrear(factura, evento.getEmail());

        try{
            emailFacturaService.enviarComprobante(evento.getEmail(), factura);
            comprobanteEmailService.marcarEnviado(comprobante);
        } catch (Exception e){
            comprobanteEmailService.marcarFallido(comprobante, e.getMessage());
            throw e;
        }
    }
}
