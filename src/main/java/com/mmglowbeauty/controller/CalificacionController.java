package com.mmglowbeauty.controller;

import com.mmglowbeauty.dto.CalificacionRequest;
import com.mmglowbeauty.service.CalificacionService;
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

@RestController
@RequestMapping("/api/calificaciones")
@SecurityRequirement(name = "bearer-jwt")
@Tag(name = "Calificaciones", description = "Sistema de calificaciones y reseñas")
public class CalificacionController {

    private static final Logger log = LoggerFactory.getLogger(CalificacionController.class);

    private final CalificacionService calificacionService;

    public CalificacionController(CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(summary = "Registrar calificación", description = "Registrar una nueva calificación")
    public ResponseEntity<Void> registrarCalificacion(@Valid @RequestBody CalificacionRequest request) {
        log.info("Solicitud de registro de calificación recibida para cita: {}", request.citaId);
        calificacionService.registrarCalificacion(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}/responder")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Responder calificación", description = "Responder a una calificación")
    public ResponseEntity<Void> responderCalificacion(@PathVariable String id, @RequestBody String respuesta) {
        log.info("Solicitud de respuesta a calificación recibida: {}", id);
        calificacionService.responderCalificacion(id, respuesta);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/promedio/{empleadaId}")
    @Operation(summary = "Calcular promedio", description = "Calcular promedio de calificaciones de una empleada")
    public ResponseEntity<Double> calcularPromedio(@PathVariable String empleadaId) {
        log.info("Solicitud de cálculo de promedio para empleada: {}", empleadaId);
        double promedio = calificacionService.calcularPromedio(empleadaId);
        return ResponseEntity.ok(promedio);
    }
}
