package com.cibertec.bruma_ms_atencion_pedidos.api.request;

import jakarta.validation.constraints.*;


public class MesaCreateRequestDto {
    @NotBlank(message = "El numero de mesa es obligatorio")
    private String numeroMesa;

    @NotNull(message = "La capacidad es obligatoria")
    @Positive (message = "La capacidad debe ser mayor a 0")
    private Integer capacidad;

    @NotBlank(message = "la ubicación es obligatoria")
    private String ubicacion;

    @NotNull(message = "El estado de la mesa es obligatorio")
    private Integer estadoMesa;

}
