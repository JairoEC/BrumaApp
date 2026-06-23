package cibertec.edu.pe.service.impl;

import cibertec.edu.pe.model.DetalleFactura;
import cibertec.edu.pe.model.Factura;
import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailFacturaService {
    private final Resend resend = new Resend("re_4GCPVVHJ_nASr1JFfT6qy2D8YnkGLMrE6");
    private final String CORREO_ORIGEN = "jespinozac96@gmail.com";

    @Async
    public void enviarCorreo(String emailCliente, Factura factura) {
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("onboarding@resend.dev")
                .to(CORREO_ORIGEN)
                .subject("Comprobante electronico - Factura #"+factura.getId())
                .html("<p>TOTAL : "+emailCliente+"</p>")
                .build();
        try {
            resend.emails().send(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
