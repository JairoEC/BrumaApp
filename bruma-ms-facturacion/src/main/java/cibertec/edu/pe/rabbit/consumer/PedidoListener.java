package cibertec.edu.pe.rabbit.consumer;

import cibertec.edu.pe.mapper.FacturaMapper;
import cibertec.edu.pe.model.ComprobanteEmail;
import cibertec.edu.pe.model.DetalleFactura;
import cibertec.edu.pe.model.Factura;
import cibertec.edu.pe.rabbit.dto.PedidoEventDto;
import cibertec.edu.pe.repository.ComprobanteEmailRepository;
import cibertec.edu.pe.repository.FacturaRepository;
import cibertec.edu.pe.service.DetalleFacturaService;
import cibertec.edu.pe.service.FacturaService;
import cibertec.edu.pe.service.impl.ComprobanteEmailService;
import cibertec.edu.pe.service.impl.DetalleFacturaServiceImp;
import cibertec.edu.pe.service.impl.EmailFacturaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PedidoListener {

    private final FacturaMapper facturaMapper;
    private final FacturaService facturaService;
    private final FacturaRepository facturaRepository;
    private final EmailFacturaService emailFacturaService;
    private final ComprobanteEmailService comprobanteEmailService;
    private final DetalleFacturaServiceImp detalleFacturaService;

    @RabbitListener(queues = "cola.pedidos.facturar")
    public void procesarPedidoRecibido(PedidoEventDto evento){
        log.info("---------INGESTANDO A CONSUMER --------");
        Factura factura = Factura.builder()
                .pedidoId(evento.getPedidoId())
                .fechaCreacion(LocalDateTime.now())
                .idMesa(evento.getMesaId())
                .idCliente(evento.getClienteId())
                .email(evento.getEmail())
                .total(evento.getTotal())
                .build();
        log.info("---------MAPPER REALIZADO --------\n");
        System.out.println("EVENTO RECIBIDO: "+evento+"\n");
        log.info(factura.getPedidoId().toString());
        log.info(factura.getIdMesa().toString());
        log.info(factura.getIdCliente().toString());
        log.info(factura.getEmail());
        log.info(factura.getTotal().toString());
        if(!facturaRepository.existsById(evento.getPedidoId())){
            facturaService.crearFactura(factura);
            log.info("----------FACTURA CREADA----------");
        }
        Factura facturaFinded = facturaRepository.findByPedidoId(factura.getPedidoId()).get();
        log.info("FACTURA ENCONTRADA: "+facturaFinded.getId());
        List<DetalleFactura> listaDetalleFactura = evento.getDetalles().stream().map(
                (det) -> DetalleFactura.builder()
                        .cantidad(det.getCantidad())
                        .precioUnitario(det.getPrecioUnitario())
                        .subTotal(det.getSubtotal())
                        .build()
        ).toList();
        log.info("-----------GUARDAR DETALLE--------------");
        List<DetalleFactura> detalleFactura = detalleFacturaService.obtenerOCrearDetalleFactura(listaDetalleFactura, facturaFinded.getId());
        log.info("DETALLE GENERADO: "+detalleFactura.size());
        log.info("----------GENERANDO BUSQUEDA DE FACTURA: "+factura.getPedidoId()+"-----------");

        //Factura facturaRegistrada = facturaRepository.findByPedidoIdWithDetalles(factura.getPedidoId()).get();
        log.info("FACTURA ES: "+facturaFinded.getId());
        ComprobanteEmail comprobante = comprobanteEmailService.obtenerOCrear(facturaFinded, evento.getEmail());
        log.info("----------FACTURA OBTENIDA----------");
        try{
            emailFacturaService.enviarCorreo(evento.getEmail(), facturaFinded);
            log.info("----------CORREO ENVIADO----------");
            comprobanteEmailService.marcarEnviado(comprobante);
            log.info("----------MARCADO COMO ENVIADO----------");
        } catch (Exception e){
            log.info("----------ALGO SALIO MAL----------");
            comprobanteEmailService.marcarFallido(comprobante, e.getMessage());
            throw e;
        }
    }
}
