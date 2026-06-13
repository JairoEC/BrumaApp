package com.cibertec.bruma_ms_atencion_pedidos.repository;

import com.cibertec.bruma_ms_atencion_pedidos.model.Empleado;
import com.cibertec.bruma_ms_atencion_pedidos.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MesaRepository extends JpaRepository<Mesa, Long> {

    List<Mesa> findByEstadoMesa(Integer estadoMesa);

}
