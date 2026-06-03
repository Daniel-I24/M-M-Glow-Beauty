package com.mmglowbeauty.controller;

import com.mmglowbeauty.dto.CitaResponse;
import com.mmglowbeauty.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@SecurityRequirement(name = "bearer-jwt")
@Tag(name = "Reportes", description = "Reportes y estadisticas del negocio")
public class ReporteController {

    private static final Logger log = LoggerFactory.getLogger(ReporteController.class);

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/ingresos")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Reporte de ingresos", description = "Obtener reporte de ingresos por rango de fechas")
    public ResponseEntity<Map<String, Object>> ingresosPorRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {

        log.info("Solicitud de reporte de ingresos recibida: {} - {}", inicio, fin);
        return ResponseEntity.ok(reporteService.ingresosPorRango(inicio, fin));
    }

    @GetMapping("/servicios-populares")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Servicios mas solicitados", description = "Obtener servicios mas populares")
    public ResponseEntity<List<Map<String, Object>>> serviciosMasSolicitados() {
        log.info("Solicitud de reporte de servicios populares recibida");
        return ResponseEntity.ok(reporteService.serviciosMasSolicitados());
    }

    @GetMapping("/historial-citas")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Historial de citas", description = "Obtener historial de citas por rango de fechas")
    public ResponseEntity<List<CitaResponse>> historialCitas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {

        log.info("Solicitud de historial de citas recibida: {} - {}", inicio, fin);
        return ResponseEntity.ok(reporteService.historialCitas(inicio, fin));
    }
}
