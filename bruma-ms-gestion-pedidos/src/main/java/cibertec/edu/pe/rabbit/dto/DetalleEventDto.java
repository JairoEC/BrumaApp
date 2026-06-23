package cibertec.edu.pe.rabbit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleEventDto implements Serializable {
    // Extraídos directamente de la clave compuesta DetallePedidoId
    private String nombreProducto;
    private Long productoId;

    // Datos cuantitativos del detalle
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
