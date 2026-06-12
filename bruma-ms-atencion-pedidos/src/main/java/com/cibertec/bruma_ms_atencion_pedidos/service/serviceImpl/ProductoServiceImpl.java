package com.cibertec.bruma_ms_atencion_pedidos.service.serviceImpl;

import com.cibertec.bruma_ms_atencion_pedidos.api.request.ProductoCreateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.request.ProductoUpdateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.response.ProductoResponseDto;
import com.cibertec.bruma_ms_atencion_pedidos.mapper.ProductoMapper;
import com.cibertec.bruma_ms_atencion_pedidos.model.Producto;
import com.cibertec.bruma_ms_atencion_pedidos.repository.ProductoRepository;
import com.cibertec.bruma_ms_atencion_pedidos.service.IProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    public ProductoResponseDto createProducto(ProductoCreateRequestDto dto) {
        Producto nuevoProducto = productoMapper.toEntity(dto);
        Producto productoGuardado = productoRepository.save(nuevoProducto);
        return productoMapper.toResponseDto(productoGuardado);
    }

    @Override
    public ProductoResponseDto updateProducto(Long id, ProductoUpdateRequestDto dto) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + id));

        productoMapper.updateEntityFromDto(dto, productoExistente);
        Producto productoActualizado = productoRepository.save(productoExistente);

        return productoMapper.toResponseDto(productoActualizado);
    }

    @Override
    public List<ProductoResponseDto> getAllProductos() {
        return productoRepository.findAll()
                .stream()
                .map(productoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDto getProductoById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + id));
        return productoMapper.toResponseDto(producto);
    }

    @Override
    public void deleteProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + id));

        // Borrado lógico (recomendado) en lugar de productoRepository.delete()
        producto.setEstado(false);
        productoRepository.save(producto);
    }


}