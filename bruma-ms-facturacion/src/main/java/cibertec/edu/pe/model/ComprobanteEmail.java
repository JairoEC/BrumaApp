package cibertec.edu.pe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComprobanteEmail {
    @Id
    private Long id;
    @OneToOne
    private Factura factura;
    private String emailDestinatario;
    @Enumerated(EnumType.STRING)
    private EnumEmail estado;
    private LocalDateTime fechaIntento;
    private String mensajeError;
    private int intentos;
}
