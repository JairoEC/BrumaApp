package cibertec.edu.pe.service.impl;

import cibertec.edu.pe.model.ComprobanteEmail;
import cibertec.edu.pe.model.EnumEmail;
import cibertec.edu.pe.model.Factura;
import cibertec.edu.pe.repository.ComprobanteEmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .build();
        return comprobanteEmailRepository.save(comprobante);
    }

    public ComprobanteEmail marcarEnviado(ComprobanteEmail comprobante){
        comprobante.setEstado(EnumEmail.ENVIADO);
        return comprobanteEmailRepository.save(comprobante);
    }

    public ComprobanteEmail marcarFallido(ComprobanteEmail comprobante,String error){
        comprobante.setEstado(EnumEmail.FALLIDO);
        comprobante.setMensajeError(error);
        return comprobanteEmailRepository.save(comprobante);
    }
    public ComprobanteEmail obtenerOCrear(Factura factura, String email){
        return comprobanteEmailRepository
                .findById(factura.getId())
                .orElseGet(() -> crearPendiente(factura, email));
    }
}
