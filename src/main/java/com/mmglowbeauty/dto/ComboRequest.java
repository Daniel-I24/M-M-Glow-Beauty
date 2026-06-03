package com.mmglowbeauty.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ComboRequest {

    @NotBlank(message = "El nombre es obligatorio")
    public String nombre;

    public String descripcion;

    @NotNull(message = "El precio del combo es obligatorio")
    @Positive(message = "El precio debe ser positivo")
    public Double precioCombo;

    @NotNull(message = "El descuento es obligatorio")
    @Positive(message = "El descuento debe ser positivo")
    public Double descuento;

    @NotEmpty(message = "Debe incluir al menos un servicio")
    @Size(min = 2, message = "Un combo debe tener al menos 2 servicios")
    public List<String> servicioIds;

    public String anuncioId;

    public ComboRequest() {
    }

    public ComboRequest(String nombre, String descripcion, Double precioCombo, Double descuento,
                        List<String> servicioIds, String anuncioId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioCombo = precioCombo;
        this.descuento = descuento;
        this.servicioIds = servicioIds;
        this.anuncioId = anuncioId;
    }
}
