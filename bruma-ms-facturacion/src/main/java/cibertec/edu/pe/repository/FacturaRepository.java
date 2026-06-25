package cibertec.edu.pe.repository;

import cibertec.edu.pe.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Optional<Factura> findByPedidoId(Long id);
    @Query("SELECT f FROM Factura f LEFT JOIN FETCH f.detalleFactura WHERE f.pedidoId = :pedidoId")
    Optional<Factura> findByPedidoIdWithDetalles(@Param("pedidoId") Long pedidoId);
}
