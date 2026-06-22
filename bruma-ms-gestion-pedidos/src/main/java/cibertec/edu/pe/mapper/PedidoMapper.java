package cibertec.edu.pe.mapper;

import cibertec.edu.pe.dto.request.PedidoCreateDto;
import cibertec.edu.pe.dto.request.DetallePedidoCreateDto;
import cibertec.edu.pe.dto.response.PedidoResponseDto;
import cibertec.edu.pe.entity.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PedidoMapper {
    Pedido toEntity(PedidoCreateDto dto);
    PedidoResponseDto toResponseDto(Pedido entity);

}
