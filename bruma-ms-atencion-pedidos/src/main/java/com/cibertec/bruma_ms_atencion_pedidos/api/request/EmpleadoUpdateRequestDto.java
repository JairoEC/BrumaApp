package com.cibertec.bruma_ms_atencion_pedidos.api.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmpleadoUpdateRequestDto {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido paterno es obligatorio")
    private String apellidoPaterno;

    private String apellidoMaterno;

    @Email(message = "El formato del email no es válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "El cargo es obligatorio")
    private String cargo;

    private Boolean estado;
}
