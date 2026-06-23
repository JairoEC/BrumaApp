package cibertec.edu.pe.api.controller;

import cibertec.edu.pe.api.dto.response.FacturaResponseDto;
import cibertec.edu.pe.model.DetalleFactura;
import cibertec.edu.pe.model.Factura;
import cibertec.edu.pe.service.impl.EmailFacturaService;
import cibertec.edu.pe.service.impl.FacturaServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/api/factura")
@RequiredArgsConstructor
public class FacturaController {
    private final FacturaServiceImp facturaServiceImp;
    private final EmailFacturaService emailFacturaService;

    @GetMapping
    public ResponseEntity<List<Factura>> listarFacturas(){
        return ResponseEntity.ok().body(facturaServiceImp.getAllFacturas());
    }

    @GetMapping("{id}")
    public ResponseEntity<Factura> buscarFactura(@RequestParam("id") Long id){
        Factura factura = facturaServiceImp.getFacturaById(id);
        return ResponseEntity.ok().body(factura);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Long> eliminarFactura(@RequestParam("id") Long id){
        facturaServiceImp.eliminarFactura(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
    @PostMapping("/notificar-correo")
    public ResponseEntity<Factura> enviarCorreoFactura(@RequestBody Factura factura){
        emailFacturaService.enviarCorreo(factura.getEmail(),factura);
        return ResponseEntity.ok().body(factura);
    }
    @PostMapping()
    public ResponseEntity<Factura> crearFactura(@RequestBody Factura factura){
        Factura nuevaFactura = facturaServiceImp.crearFactura(factura);
        return ResponseEntity.ok().body(nuevaFactura);
    }
}
