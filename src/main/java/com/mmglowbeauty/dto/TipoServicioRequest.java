package com.mmglowbeauty.dto;

import jakarta.validation.constraints.NotBlank;

public class TipoServicioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    public String nombre;

    public String descripcion;

    public TipoServicioRequest() {
    }

    public TipoServicioRequest(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
