package com.cibertec.bruma_ms_atencion_pedidos.repository;

import com.cibertec.bruma_ms_atencion_pedidos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
