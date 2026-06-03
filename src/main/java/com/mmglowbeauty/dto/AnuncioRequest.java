package com.mmglowbeauty.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AnuncioRequest {

    @NotBlank(message = "El título es obligatorio")
    public String titulo;

    public String descripcion;

    public String imagenUrl;

    @NotBlank(message = "El tipo es obligatorio")
    public String tipo;

    @NotNull(message = "La fecha de inicio es obligatoria")
    public LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    public LocalDate fechaFin;

    public AnuncioRequest() {
    }

    public AnuncioRequest(String titulo, String descripcion, String imagenUrl, String tipo,
                          LocalDate fechaInicio, LocalDate fechaFin) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
}
