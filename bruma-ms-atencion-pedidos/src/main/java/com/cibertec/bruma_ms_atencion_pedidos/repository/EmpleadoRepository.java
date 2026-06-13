package com.cibertec.bruma_ms_atencion_pedidos.repository;

import com.cibertec.bruma_ms_atencion_pedidos.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByDni(String dni);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByDni(String dni);

    Optional<Empleado> findByEmail(String username);

}
