package com.cibertec.bruma_ms_atencion_pedidos.service;

import com.cibertec.bruma_ms_atencion_pedidos.api.request.ProductoCreateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.request.ProductoUpdateRequestDto;
import com.cibertec.bruma_ms_atencion_pedidos.api.response.ProductoResponseDto;

import java.util.List;

public interface IProductoService {
    ProductoResponseDto createProducto (ProductoCreateRequestDto dto);
    ProductoResponseDto updateProducto (Long id, ProductoUpdateRequestDto dto);

    List<ProductoResponseDto> getAllProductos();
    ProductoResponseDto getProductoById (Long id);
    void deleteProducto (Long id);

}
