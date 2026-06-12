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
    // Extraídos directamente de la clave compuesta DetallePedidoId
    private Long pedidoId;
    private Long productoId;

    // Datos cuantitativos del detalle
    private BigInteger cantidad;
    private BigDecimal subtotal;
}
