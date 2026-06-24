package cibertec.edu.pe.controller;

import cibertec.edu.pe.Service.DetallePedidoService;
import cibertec.edu.pe.dto.request.DetallePedidoCreateDto;
import cibertec.edu.pe.dto.response.DetallePedidoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/api/detalle-pedido")
@RequiredArgsConstructor
public class DetallePedidoController {
    private final DetallePedidoService detallePedidoService;

    @PostMapping
    public ResponseEntity<List<DetallePedidoResponseDto>> crearDetallePedido(
            @RequestBody List<DetallePedidoCreateDto> detalleCreateDto){
        List<DetallePedidoResponseDto> detalles = detallePedidoService
                .crearDetallePedido(detalleCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(detalles);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<DetallePedidoResponseDto>> buscarDetallePedido(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(detallePedidoService.buscarDetallePedido(id));
    }
}
