package cibertec.edu.pe.service.impl;

import cibertec.edu.pe.model.DetalleFactura;
import cibertec.edu.pe.model.Factura;
import cibertec.edu.pe.repository.DetalleFacturaRepository;
import cibertec.edu.pe.repository.FacturaRepository;
import cibertec.edu.pe.service.DetalleFacturaService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleFacturaServiceImp implements DetalleFacturaService {
    private final FacturaRepository facturaRepository;
    private final DetalleFacturaRepository detalleFacturaRepository;
    @Override
    public List<DetalleFactura> getDetalleFacturaById(Long id) {
        return List.of();
    }

    @Override
    public List<DetalleFactura> crearDetalleFactura(List<DetalleFactura> detalleFactura, Long idFactura) {
        Factura factura = facturaRepository.findById(idFactura)
                .orElseThrow(()->new NotFoundException("FACTURA NO ENCONTRADA"));
        detalleFactura.forEach( det ->{
            det.setFactura(factura);
        });
        return detalleFacturaRepository.saveAll(detalleFactura);
    }

    public List<DetalleFactura> obtenerOCrearDetalleFactura(List<DetalleFactura> detalleFactura, Long idFactura){
        Factura factura = facturaRepository.findById(idFactura)
                .orElseThrow(()->new NotFoundException("FACTURA NO ENCONTRADA"));
        if(!detalleFacturaRepository.existsByFacturaId(idFactura)){
            detalleFactura.forEach( det ->{
                det.setFactura(factura);
            });
            detalleFacturaRepository.saveAll(detalleFactura);
        }
        return detalleFacturaRepository.findAllByFacturaId(idFactura);
    }

    @Override
    public void eliminarDetalleFactura(Long id) {

    }

    @Override
    public DetalleFactura updateDatalleFactura(Long id, DetalleFactura detalleFactura) {
        return null;
    }
}
