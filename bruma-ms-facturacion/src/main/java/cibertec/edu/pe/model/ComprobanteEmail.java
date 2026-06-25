package cibertec.edu.pe.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "factura_id", referencedColumnName = "id")
    @JsonIgnoreProperties("comprobanteEmail")
    private Factura factura;
    private String emailDestinatario;
    @Enumerated(EnumType.STRING)
    private EnumEmail estado;
    private LocalDateTime fechaIntento;
    private String mensajeError;
    private int intentos;
}
