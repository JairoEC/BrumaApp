package cibertec.edu.pe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FacturacionApp {
    public static void main (String[] args){
        SpringApplication.run(FacturacionApp.class, args);
    }
}
