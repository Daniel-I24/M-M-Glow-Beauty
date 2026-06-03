package com.mmglowbeauty.controller;

import com.mmglowbeauty.dto.UsuarioRequest;
import com.mmglowbeauty.service.UsuarioService;
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
@RequestMapping("/api/usuarios")
@SecurityRequirement(name = "bearer-jwt")
@Tag(name = "Usuarios", description = "Gestión de usuarios del sistema")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Crear usuario", description = "Crear un nuevo usuario (solo administradoras)")
    public ResponseEntity<UsuarioRequest> crearUsuario(@Valid @RequestBody UsuarioRequest request) {
        log.info("Solicitud de creación de usuario recibida: {}", request.correo);
        UsuarioRequest usuario = usuarioService.crearUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Actualizar usuario", description = "Actualizar información de un usuario")
    public ResponseEntity<Void> actualizarUsuario(@PathVariable String id, @Valid @RequestBody UsuarioRequest request) {
        log.info("Solicitud de actualización de usuario recibida: {}", id);
        usuarioService.actualizarUsuario(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Eliminar usuario", description = "Eliminar un usuario del sistema")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String id) {
        log.info("Solicitud de eliminación de usuario recibida: {}", id);
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADORA')")
    @Operation(summary = "Listar usuarios", description = "Obtener lista de todos los usuarios")
    public ResponseEntity<List<UsuarioRequest>> listarUsuarios() {
        log.info("Solicitud de listado de usuarios recibida");
        List<UsuarioRequest> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }
}
