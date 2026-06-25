package cibertec.edu.pe.repository;

import cibertec.edu.pe.model.ComprobanteEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComprobanteEmailRepository extends JpaRepository<ComprobanteEmail, Long> {
    ComprobanteEmail findByFacturaId(Long id);
    Boolean existsByFacturaId(Long id);
}
