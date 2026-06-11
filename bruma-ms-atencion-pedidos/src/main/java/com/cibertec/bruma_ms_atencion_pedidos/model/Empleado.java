package com.cibertec.bruma_ms_atencion_pedidos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(nullable = false, length = 100)
    private String apellidoPaterno;

    @Column( length = 100)
    private String apellidoMaterno;

    @Size(min = 8, max = 8, message = "El DNI debe tener exactamente 8 caracteres")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe contener solo números")
    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @Email(message = "El formato del email no es válido")
    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    private String cargo;

    @PastOrPresent(message = "La fecha de ingreso no puede ser una fecha futura")
    @Column(nullable = false)
    private LocalDate fechaIngreso;

    @Column(nullable = false)
    private Boolean estado=true;

}
