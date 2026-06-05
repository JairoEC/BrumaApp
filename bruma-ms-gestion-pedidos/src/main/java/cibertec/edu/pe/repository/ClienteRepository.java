package cibertec.edu.pe.repository;
import cibertec.edu.pe.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
