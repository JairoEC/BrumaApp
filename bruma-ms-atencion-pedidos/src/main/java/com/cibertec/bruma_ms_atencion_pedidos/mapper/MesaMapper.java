package com.cibertec.bruma_ms_atencion_pedidos.mapper;

import com.cibertec.bruma_ms_atencion_pedidos.api.request.MesaCreateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.request.MesaUpdateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.response.MesaResponseDto;
import com.cibertec.bruma_ms_atencion_pedidos.model.Mesa;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MesaMapper {

     Mesa toEntity (MesaCreateRequestDto dto);
     MesaResponseDto toResponseDto (Mesa mesa);

     void updateEntityFromDto (MesaUpdateRequestDto dto, @MappingTarget Mesa mesa);

}
