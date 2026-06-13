package com.cibertec.bruma_ms_atencion_pedidos.service;

import com.cibertec.bruma_ms_atencion_pedidos.api.request.MesaCreateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.request.MesaUpdateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.response.MesaResponseDto;

import java.util.List;

public interface IMesaService {
    MesaResponseDto createMesa(MesaCreateRequestDto dto);

    MesaResponseDto updateMesa(Long id, MesaUpdateRequestDto dto);

    List<MesaResponseDto> getAllMesas();

    MesaResponseDto getMesaById(Long id);

    void deleteMesa(Long id);

    List<MesaResponseDto> getMesasPorEstado(Integer estadoMesa);
}
