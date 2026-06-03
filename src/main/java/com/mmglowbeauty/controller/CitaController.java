package com.mmglowbeauty.controller;

import com.mmglowbeauty.dto.CitaRequest;
import com.mmglowbeauty.dto.CitaResponse;
import com.mmglowbeauty.service.CitaService;
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
@RequestMapping("/api/citas")
@SecurityRequirement(name = "bearer-jwt")
@Tag(name = "Citas", description = "Gestión de citas y reservas")
public class CitaController {

    private static final Logger log = LoggerFactory.getLogger(CitaController.class);

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADORA') or hasRole('CLIENTE')")
    @Operation(summary = "Crear cita", description = "Crear una nueva cita")
    public ResponseEntity<CitaResponse> crearCita(@Valid @RequestBody CitaRequest request) {
        log.info("Solicitud de creación de cita recibida");
        CitaResponse cita = citaService.crearCita(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cita);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Actualizar cita", description = "Actualizar información de una cita")
    public ResponseEntity<CitaResponse> actualizarCita(@PathVariable String id, @Valid @RequestBody CitaRequest request) {
        log.info("Solicitud de actualización de cita recibida: {}", id);
        CitaResponse cita = citaService.actualizarCita(id, request);
        return ResponseEntity.ok(cita);
    }

    @PutMapping("/{id}/cancelar")
    @PreAuthorize("hasRole('ADMINISTRADORA') or hasRole('CLIENTE')")
    @Operation(summary = "Cancelar cita", description = "Cancelar una cita existente")
    public ResponseEntity<Void> cancelarCita(@PathVariable String id) {
        log.info("Solicitud de cancelación de cita recibida: {}", id);
        citaService.cancelarCita(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/confirmar")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Confirmar cita", description = "Confirmar una cita pendiente")
    public ResponseEntity<Void> confirmarCita(@PathVariable String id) {
        log.info("Solicitud de confirmación de cita recibida: {}", id);
        citaService.confirmarCita(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Listar citas", description = "Obtener lista de todas las citas")
    public ResponseEntity<List<CitaResponse>> listarCitas() {
        log.info("Solicitud de listado de citas recibida");
        List<CitaResponse> citas = citaService.listarCitas();
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/empleada/{empleadaId}")
    @PreAuthorize("hasRole('ADMINISTRADORA') or hasRole('EMPLEADA')")
    @Operation(summary = "Listar citas por empleada", description = "Obtener citas de una empleada específica")
    public ResponseEntity<List<CitaResponse>> listarPorEmpleada(@PathVariable String empleadaId) {
        log.info("Solicitud de listado de citas para empleada: {}", empleadaId);
        List<CitaResponse> citas = citaService.listarPorEmpleada(empleadaId);
        return ResponseEntity.ok(citas);
    }
}
