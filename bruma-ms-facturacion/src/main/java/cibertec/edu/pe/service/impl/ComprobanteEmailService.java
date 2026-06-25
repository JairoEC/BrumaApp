package cibertec.edu.pe.service.impl;

import cibertec.edu.pe.model.ComprobanteEmail;
import cibertec.edu.pe.model.EnumEmail;
import cibertec.edu.pe.model.Factura;
import cibertec.edu.pe.repository.ComprobanteEmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ComprobanteEmailService {
    private final ComprobanteEmailRepository comprobanteEmailRepository;

    public ComprobanteEmail crearPendiente(Factura factura, String email){
        ComprobanteEmail comprobante = ComprobanteEmail.builder()
                .emailDestinatario(email)
                .factura(factura)
                .estado(EnumEmail.PENDIENTE)
                .mensajeError("Sin error")
                .intentos(0)
                .build();
        return comprobanteEmailRepository.save(comprobante);
    }

    public ComprobanteEmail marcarEnviado(ComprobanteEmail comprobante){
        comprobante.setEstado(EnumEmail.ENVIADO);
        comprobante.setFechaIntento(LocalDateTime.now());
        comprobante.setIntentos(comprobante.getIntentos()+1);
        return comprobanteEmailRepository.save(comprobante);
    }

    public ComprobanteEmail marcarFallido(ComprobanteEmail comprobante,String error){
        comprobante.setEstado(EnumEmail.FALLIDO);
        comprobante.setFechaIntento(LocalDateTime.now());
        comprobante.setIntentos(comprobante.getIntentos()+1);
        comprobante.setMensajeError(error);
        return comprobanteEmailRepository.save(comprobante);
    }
    public ComprobanteEmail obtenerOCrear(Factura factura, String email){
        if(!comprobanteEmailRepository.existsByFacturaId(factura.getId())){
            crearPendiente(factura, email);
        }
        ComprobanteEmail comprobanteEmail = comprobanteEmailRepository.findByFacturaId(factura.getId());
        return comprobanteEmail;
    }
}
