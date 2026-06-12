package cibertec.edu.pe.repository;

import cibertec.edu.pe.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
}
