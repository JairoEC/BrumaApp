package cibertec.edu.pe.Service;


import cibertec.edu.pe.entity.Cliente;
import cibertec.edu.pe.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() { return clienteRepository.findAll(); }

    public Optional<Cliente> buscarPorId(Long id) { return clienteRepository.findById(id); }

    public Cliente guardar(Cliente cliente) { return clienteRepository.save(cliente); }

    public Cliente actualizar(Long id, Cliente datos) {
        return clienteRepository.findById(id).map(c -> {
            c.setNombre(datos.getNombre());
            c.setApellido(datos.getApellido());
            c.setEmail(datos.getEmail());
            c.setTelefono(datos.getTelefono());
            c.setDireccion(datos.getDireccion());
            return clienteRepository.save(c);
        }).orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + id));
    }

    public void eliminar(Long id) { clienteRepository.deleteById(id); }
}
