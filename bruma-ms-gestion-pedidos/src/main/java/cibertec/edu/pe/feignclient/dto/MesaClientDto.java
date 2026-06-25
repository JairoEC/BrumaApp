package cibertec.edu.pe.feignclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MesaClientDto {
    private Long id;
    private String numeroMesa;
    private Integer capacidad;
    private String ubicacion;
    private Integer estadoMesa;
    private Boolean estado;
}
