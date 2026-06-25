package cibertec.edu.pe.rabbit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleEventDto {
    private String nombreProducto;
    private Long productoId;

    // Datos cuantitativos del detalle
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
