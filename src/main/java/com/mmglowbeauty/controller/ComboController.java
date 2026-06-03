package com.mmglowbeauty.controller;

import com.mmglowbeauty.dto.ComboRequest;
import com.mmglowbeauty.service.ComboService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/combos")
@SecurityRequirement(name = "bearer-jwt")
@Tag(name = "Combos", description = "Combos y paquetes de servicios")
public class ComboController {

    private static final Logger log = LoggerFactory.getLogger(ComboController.class);

    private final ComboService comboService;

    public ComboController(ComboService comboService) {
        this.comboService = comboService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Crear combo", description = "Crear un nuevo combo de servicios")
    public ResponseEntity<ComboRequest> crearCombo(@Valid @RequestBody ComboRequest request) {
        log.info("Solicitud de creación de combo recibida: {}", request.nombre);
        ComboRequest combo = comboService.crearCombo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(combo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Actualizar combo", description = "Actualizar información de un combo")
    public ResponseEntity<Void> actualizarCombo(@PathVariable String id, @Valid @RequestBody ComboRequest request) {
        log.info("Solicitud de actualización de combo recibida: {}", id);
        comboService.actualizarCombo(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Eliminar combo", description = "Eliminar un combo")
    public ResponseEntity<Void> eliminarCombo(@PathVariable String id) {
        log.info("Solicitud de eliminación de combo recibida: {}", id);
        comboService.eliminarCombo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar combos", description = "Obtener lista de todos los combos")
    public ResponseEntity<List<ComboRequest>> listarCombos() {
        log.info("Solicitud de listado de combos recibida");
        List<ComboRequest> combos = comboService.listarCombos();
        return ResponseEntity.ok(combos);
    }
}
