package com.cibertec.bruma_ms_atencion_pedidos.api.response;

import lombok.Data;

@Data

public class MesaResponseDto {
    private Long id;
    private String numeroMesa;
    private Integer capacidad;
    private String ubicacion;
    private Integer estadoMesa;
    private Boolean estado;
}
