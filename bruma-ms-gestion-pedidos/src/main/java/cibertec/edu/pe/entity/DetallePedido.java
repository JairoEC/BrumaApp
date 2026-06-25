package cibertec.edu.pe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonBackReference
    private Pedido pedido;

    @Column(name = "producto_id", nullable = false)
    private Long productoId;        // referencia al microservicio de productos

    private String nombreProducto;  // se guarda para no depender del otro MS
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}