package com.cibertec.bruma_ms_atencion_pedidos.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "mesas")
@Data
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroMesa;
    private Integer capacidad;
}
