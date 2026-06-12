package com.cibertec.bruma_ms_atencion_pedidos.api.response;

import lombok.Data;

@Data
public class EmpleadoResponseDto {
    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String dni;
    private String email;
    private String cargo;
}
