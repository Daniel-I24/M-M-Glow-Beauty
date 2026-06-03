package com.mmglowbeauty.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class ServicioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    public String nombre;

    public String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser positivo")
    public Double precio;

    @Positive(message = "La duración debe ser positiva")
    public int duracionMin;

    @NotBlank(message = "El ID del tipo de servicio es obligatorio")
    public String tipoServicioId;

    public String imagenUrl;

    public List<String> productoIds;

    public ServicioRequest() {
    }

    public ServicioRequest(String nombre, String descripcion, Double precio, int duracionMin,
                           String tipoServicioId, String imagenUrl, List<String> productoIds) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracionMin = duracionMin;
        this.tipoServicioId = tipoServicioId;
        this.imagenUrl = imagenUrl;
        this.productoIds = productoIds;
    }
}
