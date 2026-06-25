package cibertec.edu.pe.Service;

import cibertec.edu.pe.dto.request.PedidoCreateDto;
import cibertec.edu.pe.dto.response.PedidoResponseDto;
import cibertec.edu.pe.entity.DetallePedido;
import cibertec.edu.pe.entity.Pedido;
import cibertec.edu.pe.mapper.PedidoMapper;
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
    private final PedidoMapper pedidoMapper;

    public List<Pedido> listarTodos() { return pedidoRepository.findAll(); }

    public Optional<Pedido> buscarPorId(Long id) { return pedidoRepository.findById(id); }

    public List<Pedido> buscarPorCliente(Long clienteId) { return pedidoRepository.findByClienteId(clienteId); }

    public PedidoResponseDto crearPedido(PedidoCreateDto dto){
        Pedido nuevoPedido = pedidoMapper.toEntity(dto);
        nuevoPedido.setMesaId(dto.getMesaId());
        nuevoPedido.setMeseroId(dto.getMeseroId());
        nuevoPedido.setEstado("PENDIENTE");
        nuevoPedido.setFechaPedido(LocalDateTime.now());
        Pedido pedidoSaved = pedidoRepository.save(nuevoPedido);
        return pedidoMapper.toResponseDto(pedidoSaved);
    }

    public Pedido actualizarEstado(Long id, String estado) {
        return pedidoRepository.findById(id).map(p -> {
            p.setEstado(estado);
            return pedidoRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + id));
    }

    public void eliminar(Long id) { pedidoRepository.deleteById(id); }
}
