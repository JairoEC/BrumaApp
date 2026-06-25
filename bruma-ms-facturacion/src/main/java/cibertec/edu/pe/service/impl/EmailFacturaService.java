package cibertec.edu.pe.service.impl;

import cibertec.edu.pe.model.DetalleFactura;
import cibertec.edu.pe.model.Factura;
import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@Service
@RequiredArgsConstructor
public class EmailFacturaService {
    private final Resend resend = new Resend("re_4GCPVVHJ_nASr1JFfT6qy2D8YnkGLMrE6");
    private final String CORREO_ORIGEN = "jespinozac96@gmail.com";
    private final TemplateEngine templateEngine;

    @Async
    public void enviarCorreo(String emailCliente, Factura factura) {
        Context context = new Context();
        context.setVariable("factura", factura);

        String htmlContent = templateEngine.process("/factura", context);

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("onboarding@resend.dev")
                .to(CORREO_ORIGEN)
                .subject("Comprobante electronico - Factura #"+factura.getId())
                .html(htmlContent)
                .build();
        try {
            resend.emails().send(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
