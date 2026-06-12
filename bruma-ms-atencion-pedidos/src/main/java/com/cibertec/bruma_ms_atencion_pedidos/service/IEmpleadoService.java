package com.cibertec.bruma_ms_atencion_pedidos.service;

import com.cibertec.bruma_ms_atencion_pedidos.api.request.EmpleadoCreateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.request.EmpleadoUpdateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.response.EmpleadoResponseDto;

import java.util.List;

public interface IEmpleadoService {
    EmpleadoResponseDto createEmpleado (EmpleadoCreateRequestDto dto);
    EmpleadoResponseDto updateEmpleado (Long id, EmpleadoUpdateRequestDto dto);

    List<EmpleadoResponseDto> getAllEmpleados();
    EmpleadoResponseDto getEmpleadoById (Long id);
    void deleteEmpleado (Long id);
    EmpleadoResponseDto findByDni(String dni);
}
