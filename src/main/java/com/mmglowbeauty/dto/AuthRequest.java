package com.mmglowbeauty.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthRequest {

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    public String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    public String contrasena;

    public AuthRequest() {
    }

    public AuthRequest(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }
}
