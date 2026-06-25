package cibertec.edu.pe.feignclient.client;

import cibertec.edu.pe.feignclient.dto.EmpleadoClientDto;
import cibertec.edu.pe.feignclient.dto.MesaClientDto;
import cibertec.edu.pe.feignclient.dto.ProductoClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bruma-ms-atencion-pedidos", url = "http://localhost:8083")
public interface AtencionFeignClient {

    @GetMapping("/api/productos/{id}")
    ProductoClientDto getProductoPorId(@PathVariable("id") Long id);
    @GetMapping("/api/empleados/{id}")
    EmpleadoClientDto getEmpleadoPorId(@PathVariable("id") Long id);
    @GetMapping("/api/mesas/{id}")
    MesaClientDto getMesaPorId(@PathVariable("id") Long id);
}
