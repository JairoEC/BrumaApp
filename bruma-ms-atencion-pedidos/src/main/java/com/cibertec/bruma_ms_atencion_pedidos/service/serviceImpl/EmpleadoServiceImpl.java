package com.cibertec.bruma_ms_atencion_pedidos.service.serviceImpl;

import com.cibertec.bruma_ms_atencion_pedidos.api.request.EmpleadoCreateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.request.EmpleadoUpdateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.response.EmpleadoResponseDto;
import com.cibertec.bruma_ms_atencion_pedidos.mapper.EmpleadoMapper;
import com.cibertec.bruma_ms_atencion_pedidos.model.Empleado;
import com.cibertec.bruma_ms_atencion_pedidos.repository.EmpleadoRepositoy;
import com.cibertec.bruma_ms_atencion_pedidos.service.IEmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements IEmpleadoService {

    private final EmpleadoRepositoy empleadoRepositoy;
    private final EmpleadoMapper empleadoMapper;

    @Override
    @Transactional
    public EmpleadoResponseDto createEmpleado(EmpleadoCreateRequestDto dto) {
        if(empleadoRepositoy.existsByDni(dto.getDni())){
            throw new RuntimeException("El DNI ya se encuentra registrado");
        }
        Empleado empleado = empleadoMapper.toEntity(dto);

        Empleado empleadoGuardado = empleadoRepositoy.save(empleado);
        return empleadoMapper.toResponseDto(empleadoGuardado);
    }

    @Override
    @Transactional
    public EmpleadoResponseDto updateEmpleado(Long id, EmpleadoUpdateRequestDto dto) {
        Empleado empleadoExistente = empleadoRepositoy.findById(id)
                .orElseThrow(()->new RuntimeException("Empleado no hallado con el Id "+id));

        empleadoMapper.updateEntityFromDto(dto, empleadoExistente);

        Empleado empleadoActualizado = empleadoRepositoy.save(empleadoExistente);
        return empleadoMapper.toResponseDto(empleadoActualizado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpleadoResponseDto> getAllEmpleados() {
        return empleadoRepositoy.findAll().stream()
                .map(empleadoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EmpleadoResponseDto getEmpleadoById(Long id) {
        Empleado empleado = empleadoRepositoy.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));
        return empleadoMapper.toResponseDto(empleado);
    }

    @Override
    @Transactional
    public void deleteEmpleado(Long id) {
        if (!empleadoRepositoy.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, empleado no encontrado");
        }
        empleadoRepositoy.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public EmpleadoResponseDto findByDni(String dni) {
        Empleado empleado = empleadoRepositoy.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con DNI: " + dni));
        return empleadoMapper.toResponseDto(empleado);
    }
}
