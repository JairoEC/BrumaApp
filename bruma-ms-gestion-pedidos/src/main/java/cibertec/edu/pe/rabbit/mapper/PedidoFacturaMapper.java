package cibertec.edu.pe.rabbit.mapper;

import cibertec.edu.pe.entity.Cliente;
import cibertec.edu.pe.entity.Pedido;
import cibertec.edu.pe.rabbit.dto.PedidoEventDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PedidoFacturaMapper {

    PedidoEventDto toPedidoEventDto(Pedido pedido, Cliente cliente);
}
