package com.mmglowbeauty.controller;

import com.mmglowbeauty.dto.PromocionRequest;
import com.mmglowbeauty.service.PromocionService;
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
@RequestMapping("/api/promociones")
@SecurityRequirement(name = "bearer-jwt")
@Tag(name = "Promociones", description = "Promociones y descuentos")
public class PromocionController {

    private static final Logger log = LoggerFactory.getLogger(PromocionController.class);

    private final PromocionService promocionService;

    public PromocionController(PromocionService promocionService) {
        this.promocionService = promocionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Crear promoción", description = "Crear una nueva promoción")
    public ResponseEntity<Void> crearPromocion(@Valid @RequestBody PromocionRequest request) {
        log.info("Solicitud de creación de promoción recibida: {}", request.titulo);
        promocionService.crearPromocion(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}/vigencia")
    @Operation(summary = "Validar vigencia", description = "Verificar si una promoción está vigente")
    public ResponseEntity<Boolean> validarVigencia(@PathVariable String id) {
        log.info("Solicitud de validación de vigencia para promoción: {}", id);
        boolean vigente = promocionService.validarVigencia(id);
        return ResponseEntity.ok(vigente);
    }

    @GetMapping
    @Operation(summary = "Listar promociones", description = "Obtener lista de todas las promociones")
    public ResponseEntity<List<PromocionRequest>> listarPromociones() {
        log.info("Solicitud de listado de promociones recibida");
        List<PromocionRequest> promociones = promocionService.listarPromociones();
        return ResponseEntity.ok(promociones);
    }
}
