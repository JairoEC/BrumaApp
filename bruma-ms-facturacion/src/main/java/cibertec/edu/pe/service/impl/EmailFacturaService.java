package cibertec.edu.pe.service.impl;

import cibertec.edu.pe.model.DetalleFactura;
import cibertec.edu.pe.model.Factura;
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
    private final JavaMailSender mailSender;
    private final String CORREO_ORIGEN = "jespinozac96@gmail.com";
    @Async
    public void enviarComprobante(String emailCliente, Factura factura){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // El parámetro 'true' indica que será un correo multipart (soporta HTML)
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(CORREO_ORIGEN);
            helper.setTo(emailCliente);
            helper.setSubject("🧾 Comprobante Electrónico - Factura #" + factura.getId());

            // 💡 CONSTRUIMOS EL CUERPO EN HTML (Evita que el filtro antispam lo descarte)
            StringBuilder htmlBuilder = new StringBuilder();
            htmlBuilder.append("<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; border: 1px solid #ddd; padding: 20px;'>");
            htmlBuilder.append("<h2 style='color: #2c3e50; text-align: center;'>¡Gracias por tu compra!</h2>");
            htmlBuilder.append("<p>Hola, adjuntamos el detalle de tu consumo:</p>");
            htmlBuilder.append("<table style='width: 100%; border-collapse: collapse; margin-top: 15px;'>");
            htmlBuilder.append("<tr style='background-color: #f8f9fa;'>");
            htmlBuilder.append("<th style='border: 1px solid #ddd; padding: 8px;'>Cant.</th>");
            htmlBuilder.append("<th style='border: 1px solid #ddd; padding: 8px;'>Precio Unit.</th>");
            htmlBuilder.append("<th style='border: 1px solid #ddd; padding: 8px;'>Subtotal</th>");
            htmlBuilder.append("</tr>");

            // Recorremos la lista real de detalles que creaste en el controlador
            if (factura.getDetalleFactura() != null) {
                for (DetalleFactura detalle : factura.getDetalleFactura()) {
                    htmlBuilder.append("<tr>");
                    htmlBuilder.append("<td style='border: 1px solid #ddd; padding: 8px; text-align: center;'>").append(detalle.getCantidad()).append("</td>");
                    htmlBuilder.append("<td style='border: 1px solid #ddd; padding: 8px; text-align: right;'>$").append(detalle.getPrecioUnitario()).append("</td>");
                    htmlBuilder.append("<td style='border: 1px solid #ddd; padding: 8px; text-align: right; font-weight: bold;'>$").append(detalle.getSubTotal()).append("</td>");
                    htmlBuilder.append("</tr>");
                }
            }

            htmlBuilder.append("</table>");
            htmlBuilder.append("<p style='margin-top: 20px; font-size: 12px; color: #7f8c8d; text-align: center;'>Este es un comprobante automático emitido por el sistema de Facturación.</p>");
            htmlBuilder.append("</div>");

            // El segundo parámetro 'true' le avisa a Spring que procese el texto como HTML
            helper.setText(htmlBuilder.toString(), true);

            System.out.println("📬 Enviando correo HTML estructurado a Outlook...");
            mailSender.send(message);
            System.out.println("✅ ¡Correo despachado correctamente!");

        } catch (Exception e) {
            System.err.println("❌ ERROR REAL DURANTE EL ENVÍO: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
