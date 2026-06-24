package com.cibertec.bruma_ms_atencion_pedidos.mapper;
import com.cibertec.bruma_ms_atencion_pedidos.api.request.EmpleadoCreateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.request.EmpleadoUpdateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.response.EmpleadoResponseDto;
import com.cibertec.bruma_ms_atencion_pedidos.model.Empleado;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmpleadoMapper {
    // Convierte el DTO de creación a tu Entidad
    Empleado toEntity (EmpleadoCreateRequestDto dto);

    // Convierte la Entidad a tu DTO de respuesta
    EmpleadoResponseDto toResponseDto(Empleado empleado);

    // Metodo para actualizar una entidad existente con datos de un DTO
    void updateEntityFromDto(EmpleadoUpdateRequestDto dto, @MappingTarget Empleado empleado);
}
