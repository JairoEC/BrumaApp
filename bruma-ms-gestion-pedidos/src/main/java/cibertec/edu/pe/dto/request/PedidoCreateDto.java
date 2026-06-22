package cibertec.edu.pe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoCreateDto {
    private Long clienteId;
    private Long mesaId;
    private Long meseroId;
}
