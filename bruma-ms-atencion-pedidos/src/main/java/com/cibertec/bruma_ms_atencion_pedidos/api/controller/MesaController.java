package com.cibertec.bruma_ms_atencion_pedidos.api.controller;

import com.cibertec.bruma_ms_atencion_pedidos.api.request.MesaCreateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.request.MesaUpdateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.response.MesaResponseDto;
import com.cibertec.bruma_ms_atencion_pedidos.service.IMesaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mesas")
@RequiredArgsConstructor
public class MesaController {

    private final IMesaService mesaService;

    // 1. Listar todas las mesas
    @GetMapping
    public ResponseEntity<List<MesaResponseDto>> getAllMesas() {
        return ResponseEntity.ok(mesaService.getAllMesas());
    }

    // 2. Obtener una mesa por su ID
    @GetMapping("/{id}")
    public ResponseEntity<MesaResponseDto> getMesaById(@PathVariable Long id) {
        return ResponseEntity.ok(mesaService.getMesaById(id));
    }

    // 3. Crear una nueva mesa
    @PostMapping
    public ResponseEntity<MesaResponseDto> createMesa(@Valid @RequestBody MesaCreateRequestDto dto) {
        MesaResponseDto nuevaMesa = mesaService.createMesa(dto);
        return new ResponseEntity<>(nuevaMesa, HttpStatus.CREATED);
    }

    // 4. Actualizar una mesa existente
    @PutMapping("/{id}")
    public ResponseEntity<MesaResponseDto> updateMesa(@PathVariable Long id, @Valid @RequestBody MesaUpdateRequestDto dto) {
        return ResponseEntity.ok(mesaService.updateMesa(id, dto));
    }

    // 5. Eliminar una mesa (Borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMesa(@PathVariable Long id) {
        mesaService.deleteMesa(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // ENDPOINT EXTRA DE REGLA DE NEGOCIO
    // ==========================================

    // 6. Buscar mesas por su estado (Ej. GET /api/mesas/estado/1 para disponibles)
    @GetMapping("/estado/{estadoMesa}")
    public ResponseEntity<List<MesaResponseDto>> getMesasPorEstado(@PathVariable Integer estadoMesa) {
        return ResponseEntity.ok(mesaService.getMesasPorEstado(estadoMesa));
    }
}