package com.mmglowbeauty.controller;

import com.mmglowbeauty.dto.AnuncioRequest;
import com.mmglowbeauty.service.AnuncioService;
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
@RequestMapping("/api/anuncios")
@SecurityRequirement(name = "bearer-jwt")
@Tag(name = "Anuncios", description = "Banners y anuncios del sistema")
public class AnuncioController {

    private static final Logger log = LoggerFactory.getLogger(AnuncioController.class);

    private final AnuncioService anuncioService;

    public AnuncioController(AnuncioService anuncioService) {
        this.anuncioService = anuncioService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Crear anuncio", description = "Crear un nuevo anuncio")
    public ResponseEntity<AnuncioRequest> crearAnuncio(@Valid @RequestBody AnuncioRequest request) {
        log.info("Solicitud de creación de anuncio recibida: {}", request.titulo);
        AnuncioRequest anuncio = anuncioService.crearAnuncio(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(anuncio);
    }

    @PutMapping("/{id}/activar")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Activar anuncio", description = "Activar un anuncio existente")
    public ResponseEntity<Void> activarAnuncio(@PathVariable String id) {
        log.info("Solicitud de activación de anuncio recibida: {}", id);
        anuncioService.activarAnuncio(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/desactivar")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Desactivar anuncio", description = "Desactivar un anuncio existente")
    public ResponseEntity<Void> desactivarAnuncio(@PathVariable String id) {
        log.info("Solicitud de desactivación de anuncio recibida: {}", id);
        anuncioService.desactivarAnuncio(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/activos")
    @Operation(summary = "Listar anuncios activos", description = "Obtener lista de anuncios activos")
    public ResponseEntity<List<AnuncioRequest>> listarAnunciosActivos() {
        log.info("Solicitud de listado de anuncios activos recibida");
        List<AnuncioRequest> anuncios = anuncioService.listarAnunciosActivos();
        return ResponseEntity.ok(anuncios);
    }
}
