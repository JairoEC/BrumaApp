package cibertec.edu.pe.Service;

import cibertec.edu.pe.entity.DetallePedido;
import cibertec.edu.pe.entity.Pedido;
import cibertec.edu.pe.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public List<Pedido> listarTodos() { return pedidoRepository.findAll(); }

    public Optional<Pedido> buscarPorId(Long id) { return pedidoRepository.findById(id); }

    public List<Pedido> buscarPorCliente(Long clienteId) { return pedidoRepository.findByClienteId(clienteId); }

    public Pedido guardar(Pedido pedido) {
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado("PENDIENTE");
        // calcular total automáticamente
        if (pedido.getDetalles() != null) {
            pedido.getDetalles().forEach(d -> {
                d.setSubtotal(d.getCantidad() * d.getPrecioUnitario());
                d.setPedido(pedido);
            });
            double total = pedido.getDetalles().stream()
                    .mapToDouble(DetallePedido::getSubtotal).sum();
            pedido.setTotal(total);
        }
        return pedidoRepository.save(pedido);
    }

    public Pedido actualizarEstado(Long id, String estado) {
        return pedidoRepository.findById(id).map(p -> {
            p.setEstado(estado);
            return pedidoRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + id));
    }

    public void eliminar(Long id) { pedidoRepository.deleteById(id); }
}
