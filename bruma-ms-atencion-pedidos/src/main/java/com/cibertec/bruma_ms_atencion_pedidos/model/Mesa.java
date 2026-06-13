package com.cibertec.bruma_ms_atencion_pedidos.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mesas")

public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_mesa", nullable = false, length = 2)
    private String numeroMesa;

    @Column(nullable = false)
    private Integer capacidad;

    @Column(nullable = false)
    private String ubicacion;

    @Column(name="estado_mesa")
    private Integer estadoMesa;

    private Boolean estado=true;
}
