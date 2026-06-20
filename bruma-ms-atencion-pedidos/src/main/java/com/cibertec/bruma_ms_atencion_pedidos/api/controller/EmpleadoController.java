package com.cibertec.bruma_ms_atencion_pedidos.api.controller;

import com.cibertec.bruma_ms_atencion_pedidos.api.request.EmpleadoCreateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.request.EmpleadoUpdateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.response.EmpleadoResponseDto;
import com.cibertec.bruma_ms_atencion_pedidos.service.IEmpleadoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    private final IEmpleadoService empleadoService;

    @PostMapping
    public ResponseEntity<EmpleadoResponseDto> crear(@Valid @RequestBody EmpleadoCreateRequestDto dto){
        return new ResponseEntity<>(empleadoService.createEmpleado(dto),
                HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDto> actualizar(@PathVariable Long id,
                                                          @Valid @RequestBody EmpleadoUpdateRequestDto dto) {
        return ResponseEntity.ok(empleadoService.updateEmpleado(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.getEmpleadoById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDto>> listarTodos() {
        return ResponseEntity.ok(empleadoService.getAllEmpleados());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        empleadoService.deleteEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}
