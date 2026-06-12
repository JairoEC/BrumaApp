package cibertec.edu.pe.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private Pedido pedido;

    @Column(name = "producto_id", nullable = false)
    private Long productoId;        // referencia al microservicio de productos

    private String nombreProducto;  // se guarda para no depender del otro MS
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}