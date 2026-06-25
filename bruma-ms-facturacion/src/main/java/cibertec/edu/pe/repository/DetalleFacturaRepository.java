package cibertec.edu.pe.repository;

import cibertec.edu.pe.model.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {
    Boolean existsByFacturaId(Long id);
    List<DetalleFactura> findAllByFacturaId(Long id);
}
