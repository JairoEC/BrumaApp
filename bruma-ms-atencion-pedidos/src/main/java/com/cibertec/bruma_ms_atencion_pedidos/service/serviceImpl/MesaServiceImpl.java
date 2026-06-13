package com.cibertec.bruma_ms_atencion_pedidos.service.serviceImpl;

import com.cibertec.bruma_ms_atencion_pedidos.api.request.MesaCreateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.request.MesaUpdateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.response.MesaResponseDto;
import com.cibertec.bruma_ms_atencion_pedidos.mapper.MesaMapper;
import com.cibertec.bruma_ms_atencion_pedidos.model.Mesa;
import com.cibertec.bruma_ms_atencion_pedidos.repository.MesaRepository;
import com.cibertec.bruma_ms_atencion_pedidos.service.IMesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MesaServiceImpl implements IMesaService {

    private final MesaRepository mesaRepository;
    private final MesaMapper mesaMapper;

    @Override
    public MesaResponseDto createMesa(MesaCreateRequestDto dto) {
        Mesa nuevaMesa = mesaMapper.toEntity(dto);
        Mesa mesaGuardada = mesaRepository.save(nuevaMesa);
        return mesaMapper.toResponseDto(mesaGuardada);
    }

    @Override
    public MesaResponseDto updateMesa(Long id, MesaUpdateRequestDto dto) {
        Mesa mesaExistente = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con el ID: " + id));

        mesaMapper.updateEntityFromDto(dto, mesaExistente);
        Mesa mesaActualizada = mesaRepository.save(mesaExistente);

        return mesaMapper.toResponseDto(mesaActualizada);
    }

    @Override
    public List<MesaResponseDto> getAllMesas() {
        return mesaRepository.findAll()
                .stream()
                .map(mesaMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public MesaResponseDto getMesaById(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con el ID: " + id));
        return mesaMapper.toResponseDto(mesa);
    }

    @Override
    public void deleteMesa(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con el ID: " + id));

        // Borrado lógico
        mesa.setEstado(false);
        mesaRepository.save(mesa);
    }

    @Override
    public List<MesaResponseDto> getMesasPorEstado(Integer estadoMesa) {
        return mesaRepository.findByEstadoMesa(estadoMesa)
                .stream()
                .map(mesaMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}