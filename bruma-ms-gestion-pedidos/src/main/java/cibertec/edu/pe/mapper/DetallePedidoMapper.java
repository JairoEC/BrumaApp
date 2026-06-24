package cibertec.edu.pe.mapper;

import cibertec.edu.pe.dto.request.DetallePedidoCreateDto;
import cibertec.edu.pe.dto.request.DetallePedidoUpdateDto;
import cibertec.edu.pe.dto.response.DetallePedidoResponseDto;
import cibertec.edu.pe.entity.DetallePedido;
import cibertec.edu.pe.entity.Pedido;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DetallePedidoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    DetallePedido toEntity(DetallePedidoCreateDto dto);
    @Mapping(target = "pedidoId", source="pedido.id")
    DetallePedidoResponseDto toResponseDto(DetallePedido dto);

    //void updateEntityFromDto (ProductoUpdateRequestDto dto, @MappingTarget Producto producto);
    void updateEntityFromDto(DetallePedidoUpdateDto dto, @MappingTarget DetallePedido detallePedido);
    List<DetallePedidoResponseDto> toResponseDtoList(List<DetallePedido> detalles);
}
