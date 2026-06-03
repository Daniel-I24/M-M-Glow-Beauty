package com.mmglowbeauty.controller;

import com.mmglowbeauty.dto.ServicioRequest;
import com.mmglowbeauty.service.ServicioService;
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
@RequestMapping("/api/servicios")
@SecurityRequirement(name = "bearer-jwt")
@Tag(name = "Servicios", description = "Catálogo de servicios de belleza")
public class ServicioController {

    private static final Logger log = LoggerFactory.getLogger(ServicioController.class);

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Crear servicio", description = "Crear un nuevo servicio")
    public ResponseEntity<ServicioRequest> crearServicio(@Valid @RequestBody ServicioRequest request) {
        log.info("Solicitud de creación de servicio recibida: {}", request.nombre);
        ServicioRequest servicio = servicioService.crearServicio(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Actualizar servicio", description = "Actualizar información de un servicio")
    public ResponseEntity<Void> actualizarServicio(@PathVariable String id, @Valid @RequestBody ServicioRequest request) {
        log.info("Solicitud de actualización de servicio recibida: {}", id);
        servicioService.actualizarServicio(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Eliminar servicio", description = "Eliminar un servicio del catálogo")
    public ResponseEntity<Void> eliminarServicio(@PathVariable String id) {
        log.info("Solicitud de eliminación de servicio recibida: {}", id);
        servicioService.eliminarServicio(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar servicios", description = "Obtener lista de todos los servicios")
    public ResponseEntity<List<ServicioRequest>> listarServicios() {
        log.info("Solicitud de listado de servicios recibida");
        List<ServicioRequest> servicios = servicioService.listarServicios();
        return ResponseEntity.ok(servicios);
    }
}
