package cibertec.edu.pe.service;

import cibertec.edu.pe.model.DetalleFactura;

import java.util.List;

public interface DetalleFacturaService {
    List<DetalleFactura> getDetalleFacturaById(Long id);
    DetalleFactura crearDetalleFactura(DetalleFactura detalleFactura);
    void eliminarDetalleFactura(Long id);
    DetalleFactura updateDatalleFactura(Long id, DetalleFactura detalleFactura);
}
