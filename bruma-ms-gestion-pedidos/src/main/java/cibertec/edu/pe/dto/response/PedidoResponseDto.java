package cibertec.edu.pe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoResponseDto {
    private Long id;
    private Long clienteId;
    private Long mesaId;
    private Long meseroId;
    private LocalDateTime fechaPedido;

}
