package cibertec.edu.pe.mapper;

import cibertec.edu.pe.model.DetalleFactura;
import cibertec.edu.pe.model.Factura;
import cibertec.edu.pe.rabbit.dto.DetalleEventDto;
import cibertec.edu.pe.rabbit.dto.PedidoEventDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FacturaMapper {
    @Mapping(source = "mesaId", target = "idMesa")
    @Mapping(source = "total", target = "total")
    Factura toEntity(PedidoEventDto dto);

    @Mapping(source = "subtotal", target= "subTotal")
    DetalleFactura toDetalleEntity(DetalleEventDto dto);

}
