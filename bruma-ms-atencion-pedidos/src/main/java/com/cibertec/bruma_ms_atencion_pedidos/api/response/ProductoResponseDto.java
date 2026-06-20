package com.cibertec.bruma_ms_atencion_pedidos.api.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoResponseDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String categoria;
    private Integer stock;
    private Boolean estado;
}
