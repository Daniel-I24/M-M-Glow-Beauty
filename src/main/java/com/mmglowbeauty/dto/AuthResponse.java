package com.mmglowbeauty.dto;

public class AuthResponse {

    public String token;
    public String rol;
    public String nombre;

    public AuthResponse() {
    }

    public AuthResponse(String token, String rol, String nombre) {
        this.token = token;
        this.rol = rol;
        this.nombre = nombre;
    }
}
