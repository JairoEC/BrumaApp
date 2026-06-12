package com.cibertec.bruma_ms_atencion_pedidos.api.controller;

import com.cibertec.bruma_ms_atencion_pedidos.api.request.ProductoCreateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.request.ProductoUpdateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.response.ProductoResponseDto;
import com.cibertec.bruma_ms_atencion_pedidos.service.IProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final IProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDto>> getAllProductos() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> getProductoById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.getProductoById(id));
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDto> createProducto(@Valid @RequestBody ProductoCreateRequestDto dto) {
        ProductoResponseDto nuevoProducto = productoService.createProducto(dto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> updateProducto(@PathVariable Long id, @Valid @RequestBody ProductoUpdateRequestDto dto) {
        return ResponseEntity.ok(productoService.updateProducto(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }
}