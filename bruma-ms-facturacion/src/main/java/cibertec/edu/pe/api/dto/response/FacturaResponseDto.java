package cibertec.edu.pe.api.dto.response;

import cibertec.edu.pe.model.DetalleFactura;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaResponseDto {
    private Long id;
    private LocalDateTime fechaCreacion;
    private Double total;
    private List<DetalleFactura> detalleFactura;
}
