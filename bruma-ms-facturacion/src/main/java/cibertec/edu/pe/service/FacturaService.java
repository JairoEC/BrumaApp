package cibertec.edu.pe.service;

import cibertec.edu.pe.model.Factura;

import java.util.List;

public interface FacturaService {
    List<Factura> getAllFacturas();
    Factura getFacturaById(Long id);
    Factura crearFactura(Factura factura);
    void eliminarFactura(Long id);
    Factura updateFactura(Long id, Factura factura);
}
