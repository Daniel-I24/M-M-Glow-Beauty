package com.mmglowbeauty.service;

import com.mmglowbeauty.dto.ProductoResponse;

import java.util.List;

public interface InventarioService {
    void registrarEntrada(String id, int cantidad);
    void registrarSalida(String id, int cantidad);
    List<ProductoResponse> verificarStockBajo();
    void generarAlerta(String productoId);
    List<ProductoResponse> listarProductos();
}
