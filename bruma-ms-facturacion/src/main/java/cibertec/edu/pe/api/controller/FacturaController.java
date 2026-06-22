package cibertec.edu.pe.api.controller;

import cibertec.edu.pe.model.DetalleFactura;
import cibertec.edu.pe.model.Factura;
import cibertec.edu.pe.service.impl.EmailFacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/factura")
@RequiredArgsConstructor
public class FacturaController {
    private final EmailFacturaService emailFacturaService;

    @GetMapping
    public ResponseEntity<Factura> obtenerFactura(){
        // 1. Instanciar el detalle usando Builder
        DetalleFactura detalleFactura = DetalleFactura.builder()
                .id(1L)
                .cantidad(3)
                .precioUnitario(13.0)
                .subTotal(39.0)
                .build();

        // 2. Instanciar la factura
        Factura factura = Factura.builder()
                .id(1L)
                .fechaCreacion(LocalDateTime.now())
                .idMesa(1L)
                .idCliente(12L)
                .total(39.0)
                .detalleFactura(List.of(detalleFactura))
                .build();

        // 3. VINCULACIÓN BIDIRECCIONAL MANUAL:
        // Como estás creando el objeto en memoria (sin pasar por el servicio/BD),
        // debes decirle al detalle quién es su factura padre, de lo contrario
        // podría romperse al intentar leer los datos en el método del correo.
        detalleFactura.setFactura(factura);

        try {
            System.out.println("====== INICIANDO ENVÍO DE CORREO DE PRUEBA ======");
            emailFacturaService.enviarComprobante("jespinozac96@outlook.com", factura);
            System.out.println("====== EL MÉTODO SE EJECUTÓ SIN CAÍDAS ======");
        } catch (Exception e) {
            System.err.println("❌ ERROR CRÍTICO AL ENVIAR CORREO: " + e.getMessage());
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.OK).body(factura);
    }

}
