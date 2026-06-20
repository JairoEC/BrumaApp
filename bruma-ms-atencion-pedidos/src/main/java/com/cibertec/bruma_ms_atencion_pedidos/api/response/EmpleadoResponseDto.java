package com.cibertec.bruma_ms_atencion_pedidos.api.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmpleadoResponseDto {
    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String dni;
    private String email;
    private String cargo;
    private LocalDate fechaIngreso;
    private Boolean estado;
}
