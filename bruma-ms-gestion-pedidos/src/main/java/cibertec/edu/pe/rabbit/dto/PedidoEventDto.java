package cibertec.edu.pe.rabbit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoEventDto implements Serializable {
    private Long clienteId;
    private String clienteDni;
    private String email;

    private Long pedidoId;
    private Long mesaId;
    private BigDecimal total;

    private List<DetalleEventDto> detalles;
}
