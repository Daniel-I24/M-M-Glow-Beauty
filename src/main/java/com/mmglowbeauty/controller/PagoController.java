package com.mmglowbeauty.controller;

import com.mmglowbeauty.dto.PagoRequest;
import com.mmglowbeauty.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@SecurityRequirement(name = "bearer-jwt")
@Tag(name = "Pagos", description = "Gestión de pagos y comprobantes")
public class PagoController {

    private static final Logger log = LoggerFactory.getLogger(PagoController.class);

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Registrar pago", description = "Registrar un nuevo pago")
    public ResponseEntity<Void> registrarPago(@Valid @RequestBody PagoRequest request) {
        log.info("Solicitud de registro de pago recibida para cita: {}", request.citaId);
        pagoService.registrarPago(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}/comprobante")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Generar comprobante", description = "Generar comprobante de pago")
    public ResponseEntity<byte[]> generarComprobante(@PathVariable String id) {
        log.info("Solicitud de generación de comprobante recibida para pago: {}", id);
        byte[] comprobante = pagoService.generarComprobante(id);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "comprobante_" + id + ".txt");
        
        return ResponseEntity.ok().headers(headers).body(comprobante);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Listar pagos", description = "Obtener lista de todos los pagos")
    public ResponseEntity<List<PagoRequest>> listarPagos() {
        log.info("Solicitud de listado de pagos recibida");
        List<PagoRequest> pagos = pagoService.listarPagos();
        return ResponseEntity.ok(pagos);
    }
}
