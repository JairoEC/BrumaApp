package com.cibertec.bruma_ms_atencion_pedidos.mapper;

import com.cibertec.bruma_ms_atencion_pedidos.api.request.ProductoCreateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.request.ProductoUpdateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.response.ProductoResponseDto;
import com.cibertec.bruma_ms_atencion_pedidos.model.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductoMapper {
    Producto toEntity (ProductoCreateRequestDto dto);

    ProductoResponseDto toResponseDto (Producto producto);

    void updateEntityFromDto (ProductoUpdateRequestDto dto, @MappingTarget Producto producto);
}
