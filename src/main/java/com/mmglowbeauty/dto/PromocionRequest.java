package com.mmglowbeauty.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class PromocionRequest {

    @NotBlank(message = "El título es obligatorio")
    public String titulo;

    public String descripcion;

    @NotNull(message = "El descuento es obligatorio")
    @Positive(message = "El descuento debe ser positivo")
    public Double descuento;

    @NotNull(message = "La fecha de inicio es obligatoria")
    public LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    public LocalDate fechaFin;

    public String anuncioId;

    public PromocionRequest() {
    }

    public PromocionRequest(String titulo, String descripcion, Double descuento,
                            LocalDate fechaInicio, LocalDate fechaFin, String anuncioId) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.descuento = descuento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.anuncioId = anuncioId;
    }
}
