package com.mmglowbeauty.service.impl;

import com.mmglowbeauty.dto.ProductoResponse;
import com.mmglowbeauty.exception.RecursoNoEncontradoException;
import com.mmglowbeauty.model.Producto;
import com.mmglowbeauty.repository.ProductoRepository;
import com.mmglowbeauty.service.InventarioService;
import com.mmglowbeauty.service.NotificacionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InventarioServiceImpl implements InventarioService {

    private static final Logger log = LoggerFactory.getLogger(InventarioServiceImpl.class);

    private final ProductoRepository productoRepository;
    private final NotificacionStrategy notificacionStrategy;

    public InventarioServiceImpl(ProductoRepository productoRepository, NotificacionStrategy notificacionStrategy) {
        this.productoRepository = productoRepository;
        this.notificacionStrategy = notificacionStrategy;
    }

    @Override
    public void registrarEntrada(String id, int cantidad) {
        log.info("Registrando entrada de {} unidades para producto: {}", cantidad, id);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id));

        producto.setCantidad(producto.getCantidad() + cantidad);
        productoRepository.save(producto);
        log.info("Entrada registrada. Nueva cantidad: {}", producto.getCantidad());
    }

    @Override
    public void registrarSalida(String id, int cantidad) {
        log.info("Registrando salida de {} unidades para producto: {}", cantidad, id);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id));

        int nuevaCantidad = producto.getCantidad() - cantidad;
        producto.setCantidad(nuevaCantidad);

        if (nuevaCantidad < producto.getCantidadMinima()) {
            generarAlerta(id);
        }

        productoRepository.save(producto);
        log.info("Salida registrada. Nueva cantidad: {}", producto.getCantidad());
    }

    @Override
    public List<ProductoResponse> verificarStockBajo() {
        log.info("Verificando productos con stock bajo");
        return productoRepository.findAll().stream()
                .filter(p -> p.getCantidad() < p.getCantidadMinima())
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void generarAlerta(String productoId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id: " + productoId));

        String mensaje = String.format("ALERTA: El producto '%s' tiene stock bajo. Cantidad actual: %d, Mínima: %d",
                producto.getNombre(), producto.getCantidad(), producto.getCantidadMinima());

        log.warn("Stock bajo para producto: {}", producto.getNombre());
        notificacionStrategy.enviar("admin@mmglowbeauty.com", mensaje);
    }

    @Override
    public List<ProductoResponse> listarProductos() {
        log.info("Listando productos");
        return productoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private ProductoResponse toResponse(Producto producto) {
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getCantidad(),
                producto.getCantidadMinima(),
                producto.getPrecioUnitario(),
                producto.getProveedor()
        );
    }
}
