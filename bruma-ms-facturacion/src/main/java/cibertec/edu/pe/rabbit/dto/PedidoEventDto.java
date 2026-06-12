package cibertec.edu.pe.rabbit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoEventDto {
    private Long pedidoId;
    private Long mesaId;
    private String email;
    private BigDecimal total;
    private List<DetalleEventDto> detalles;
}
