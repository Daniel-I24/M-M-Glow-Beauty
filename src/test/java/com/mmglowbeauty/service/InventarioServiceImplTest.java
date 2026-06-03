package com.mmglowbeauty.service;

import com.mmglowbeauty.model.Producto;
import com.mmglowbeauty.repository.ProductoRepository;
import com.mmglowbeauty.service.impl.InventarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private NotificacionStrategy notificacionStrategy;

    @InjectMocks
    private InventarioServiceImpl inventarioService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId("prod123");
        producto.setNombre("Esmalte Rojo");
        producto.setCantidad(50);
        producto.setCantidadMinima(10);
    }

    @Test
    void testRegistrarEntrada_exitoso() {
        when(productoRepository.findById("prod123")).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        inventarioService.registrarEntrada("prod123", 20);

        assertEquals(70, producto.getCantidad());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void testRegistrarSalida_stockNormal() {
        when(productoRepository.findById("prod123")).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        inventarioService.registrarSalida("prod123", 20);

        assertEquals(30, producto.getCantidad());
        verify(productoRepository, times(1)).save(producto);
        verify(notificacionStrategy, never()).enviar(anyString(), anyString());
    }

    @Test
    void testRegistrarSalida_disparaAlerta() {
        when(productoRepository.findById("prod123")).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        inventarioService.registrarSalida("prod123", 45);

        assertEquals(5, producto.getCantidad());
        verify(productoRepository, times(1)).save(producto);
        verify(notificacionStrategy, times(1)).enviar(anyString(), anyString());
    }
}
