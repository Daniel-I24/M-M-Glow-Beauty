package com.mmglowbeauty.controller;

import com.mmglowbeauty.dto.ProductoResponse;
import com.mmglowbeauty.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@SecurityRequirement(name = "bearer-jwt")
@Tag(name = "Inventario", description = "Gestión de inventario de productos")
public class InventarioController {

    private static final Logger log = LoggerFactory.getLogger(InventarioController.class);

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @PutMapping("/{id}/entrada")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Registrar entrada", description = "Registrar entrada de productos al inventario")
    public ResponseEntity<Void> registrarEntrada(@PathVariable String id, @RequestParam int cantidad) {
        log.info("Solicitud de entrada de inventario recibida para producto: {}", id);
        inventarioService.registrarEntrada(id, cantidad);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/salida")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Registrar salida", description = "Registrar salida de productos del inventario")
    public ResponseEntity<Void> registrarSalida(@PathVariable String id, @RequestParam int cantidad) {
        log.info("Solicitud de salida de inventario recibida para producto: {}", id);
        inventarioService.registrarSalida(id, cantidad);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stock-bajo")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Verificar stock bajo", description = "Obtener productos con stock bajo")
    public ResponseEntity<List<ProductoResponse>> verificarStockBajo() {
        log.info("Solicitud de verificación de stock bajo recibida");
        List<ProductoResponse> productos = inventarioService.verificarStockBajo();
        return ResponseEntity.ok(productos);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Listar productos", description = "Obtener lista de todos los productos")
    public ResponseEntity<List<ProductoResponse>> listarProductos() {
        log.info("Solicitud de listado de productos recibida");
        List<ProductoResponse> productos = inventarioService.listarProductos();
        return ResponseEntity.ok(productos);
    }
}
