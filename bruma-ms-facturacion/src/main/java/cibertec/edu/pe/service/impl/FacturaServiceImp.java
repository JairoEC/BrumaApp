package cibertec.edu.pe.service.impl;

import cibertec.edu.pe.model.Factura;
import cibertec.edu.pe.repository.FacturaRepository;
import cibertec.edu.pe.service.FacturaService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturaServiceImp implements FacturaService {
    private final FacturaRepository facturaRepository;
    @Override
    public List<Factura> getAllFacturas() {
        return facturaRepository.findAll();
    }

    @Override
    public Factura getFacturaById(Long id) {
        Factura factura = facturaRepository.findById(id).
                orElseThrow(() -> new RuntimeException());
        return factura;
    }

    @Override
    public Factura crearFactura(Factura factura) {
        factura.getDetalleFactura().forEach(
                detalle -> detalle.setFactura(factura)
        );
        Factura facturaGuardada = facturaRepository.save(factura);
        return facturaGuardada;
    }

    @Override
    public void eliminarFactura(Long id) {

    }

    @Override
    public Factura updateFactura(Long id, Factura factura) {
        return null;
    }
}
