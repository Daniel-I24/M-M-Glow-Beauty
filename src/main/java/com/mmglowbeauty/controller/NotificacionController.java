package com.mmglowbeauty.controller;

import com.mmglowbeauty.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notificaciones")
@SecurityRequirement(name = "bearer-jwt")
@Tag(name = "Notificaciones", description = "Envio de notificaciones masivas")
public class NotificacionController {

    private static final Logger log = LoggerFactory.getLogger(NotificacionController.class);

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping("/masiva")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Enviar notificacion masiva", description = "Enviar notificacion a todos los clientes")
    public ResponseEntity<Map<String, Object>> enviarNotificacionMasiva(@RequestBody Map<String, String> body) {
        String mensaje = body.get("mensaje");
        log.info("Solicitud de notificacion masiva recibida");
        return ResponseEntity.ok(notificacionService.enviarNotificacionMasiva(mensaje));
    }
}
