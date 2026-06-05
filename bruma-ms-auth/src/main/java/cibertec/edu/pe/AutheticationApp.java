package cibertec.edu.pe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AutheticationApp {

    public static void main(String[] args) {
        SpringApplication.run(AutheticationApp.class, args);
    }
}