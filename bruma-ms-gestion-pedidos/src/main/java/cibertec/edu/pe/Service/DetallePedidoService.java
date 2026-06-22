package cibertec.edu.pe.Service;

import cibertec.edu.pe.dto.request.DetallePedidoCreateDto;
import cibertec.edu.pe.dto.request.DetallePedidoUpdateDto;
import cibertec.edu.pe.dto.response.DetallePedidoResponseDto;
import cibertec.edu.pe.entity.DetallePedido;
import cibertec.edu.pe.entity.Pedido;
import cibertec.edu.pe.mapper.DetallePedidoMapper;
import cibertec.edu.pe.repository.DetallePedidoRepository;
import cibertec.edu.pe.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetallePedidoService {
    private final DetallePedidoRepository detallePedidoRepository;
    private final DetallePedidoMapper detallePedidoMapper;
    private final PedidoRepository pedidoRepository;

    public List<DetallePedidoResponseDto> crearDetallePedido(List<DetallePedidoCreateDto> detalles){
        List<DetallePedido> listaDetalles = detalles.stream().map(
                (det)-> detallePedidoMapper.toEntity(det)).toList();
        BigDecimal totalVenta = listaDetalles.stream()
                .map(det -> {
                    // 1. Calcular el subtotal de este objeto específico
                    BigDecimal subtotal = det.getPrecioUnitario().multiply(BigDecimal.valueOf(det.getCantidad()));

                    // 2. Setear el subtotal en el objeto actual
                    det.setSubtotal(subtotal);

                    // 3. Retornar el subtotal para que el Stream lo acumule después
                    return subtotal;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        listaDetalles.forEach(detallePedido -> detallePedidoRepository.save(detallePedido));
        return listaDetalles.stream().map(det->detallePedidoMapper.toResponseDto(det)).toList();
    }

    @Transactional
    public List<DetallePedidoResponseDto> updateDetalle(Long id, List<DetallePedidoUpdateDto> detallesDto) {
        // 1. Validar que existan detalles para ese ID de pedido
        List<DetallePedido> detallesExistentes = detallePedidoRepository.findByPedidoId(id);
        if (detallesExistentes == null || detallesExistentes.isEmpty()) {
            throw new RuntimeException("Pedido no encontrado o sin detalles: " + id); // Se agregó 'throw'
        }

        // 2. Procesar y actualizar cada detalle emparejando por ID
        List<DetallePedido> detallesActualizados = detallesDto.stream()
                .map(dto -> {
                    // Buscar el detalle existente que coincida con el ID del DTO
                    DetallePedido detalle = detallesExistentes.stream()
                            .filter(d -> d.getId().equals(dto.getId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado con ID: " + dto.getId()));

                    // Usar el mapper para actualizar los campos modificados (precio, cantidad, etc.)
                    detallePedidoMapper.updateEntityFromDto(dto, detalle);

                    // Recalcular el subtotal automáticamente con la lógica BigDecimal
                    BigDecimal subtotal = detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad()));
                    detalle.setSubtotal(subtotal);

                    return detalle;
                })
                .collect(Collectors.toList());

        // 3. Guardar los cambios en la base de datos
        List<DetallePedido> entidadesGuardadas = detallePedidoRepository.saveAll(detallesActualizados);

        // 4. Retornar la lista convertida a ResponseDto
        return detallePedidoMapper.toResponseDtoList(entidadesGuardadas);
    }

    @Transactional
    public void deleteDetalle(Long detalleId) {
        // 1. Buscar el detalle que se va a eliminar
        DetallePedido detalle = detallePedidoRepository.findById(detalleId)
                .orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado con ID: " + detalleId));

        // 2. Obtener la referencia al Pedido antes de borrar el detalle
        Pedido pedido = detalle.getPedido(); // Asumiendo que tienes la relación @ManyToOne en tu entidad DetallePedido

        // 3. Eliminar el registro del detalle
        detallePedidoRepository.delete(detalle);

        // 4. Forzar la eliminación en la base de datos para que el cálculo posterior sea exacto
        detallePedidoRepository.flush();

        // 5. Recalcular y actualizar el total del Pedido principal
        this.actualizarTotalPedido(pedido.getId());
    }

    private void actualizarTotalPedido(Long pedidoId) {
        // 1. Buscar el pedido principal
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + pedidoId));

        // 2. Volver a consultar los detalles que quedan vigentes en la BD
        List<DetallePedido> detallesVigentes = detallePedidoRepository.findByPedidoId(pedidoId);

        // 3. Sumar todos los subtotales usando Streams y BigDecimal
        BigDecimal nuevoTotal = detallesVigentes.stream()
                .map(DetallePedido::getSubtotal)
                .filter(Objects::nonNull) // Evita NullPointerException si algún subtotal vino vacío
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. Asignar el nuevo total general al pedido y guardarlo
        pedido.setTotal(nuevoTotal);
        pedidoRepository.save(pedido);
    }

}
