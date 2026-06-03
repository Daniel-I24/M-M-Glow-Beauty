package com.mmglowbeauty.controller;

import com.mmglowbeauty.dto.TipoServicioRequest;
import com.mmglowbeauty.service.TipoServicioService;
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
@RequestMapping("/api/tipos-servicio")
@SecurityRequirement(name = "bearer-jwt")
@Tag(name = "Tipos de Servicio", description = "Categorías de servicios")
public class TipoServicioController {

    private static final Logger log = LoggerFactory.getLogger(TipoServicioController.class);

    private final TipoServicioService tipoServicioService;

    public TipoServicioController(TipoServicioService tipoServicioService) {
        this.tipoServicioService = tipoServicioService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Crear tipo de servicio", description = "Crear un nuevo tipo de servicio")
    public ResponseEntity<TipoServicioRequest> crearTipoServicio(@Valid @RequestBody TipoServicioRequest request) {
        log.info("Solicitud de creación de tipo de servicio recibida: {}", request.nombre);
        TipoServicioRequest tipoServicio = tipoServicioService.crearTipoServicio(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoServicio);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Actualizar tipo de servicio", description = "Actualizar información de un tipo de servicio")
    public ResponseEntity<Void> actualizarTipoServicio(@PathVariable String id, @Valid @RequestBody TipoServicioRequest request) {
        log.info("Solicitud de actualización de tipo de servicio recibida: {}", id);
        tipoServicioService.actualizarTipoServicio(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Eliminar tipo de servicio", description = "Eliminar un tipo de servicio")
    public ResponseEntity<Void> eliminarTipoServicio(@PathVariable String id) {
        log.info("Solicitud de eliminación de tipo de servicio recibida: {}", id);
        tipoServicioService.eliminarTipoServicio(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar tipos de servicio", description = "Obtener lista de todos los tipos de servicio")
    public ResponseEntity<List<TipoServicioRequest>> listarTiposServicio() {
        log.info("Solicitud de listado de tipos de servicio recibida");
        List<TipoServicioRequest> tipos = tipoServicioService.listarTiposServicio();
        return ResponseEntity.ok(tipos);
    }
}
