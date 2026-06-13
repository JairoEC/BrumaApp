package com.cibertec.bruma_ms_atencion_pedidos.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

// 2. DTO para ACTUALIZAR
@Data
public class MesaUpdateRequestDto {
    @NotBlank(message = "El número de mesa es obligatorio")
    private String numeroMesa;

    @NotNull(message = "La capacidad es obligatoria")
    @Positive(message = "La capacidad debe ser mayor a 0")
    private Integer capacidad;

    @NotBlank(message = "La ubicación es obligatoria")
    private String ubicacion;

    @NotNull(message = "El estado de la mesa es obligatorio")
    private Integer estadoMesa;

    @NotNull(message = "El estado general (activo/inactivo) es obligatorio")
    private Boolean estado;
}
