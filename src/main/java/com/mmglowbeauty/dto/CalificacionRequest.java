package com.mmglowbeauty.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CalificacionRequest {

    @NotBlank(message = "El ID de la cita es obligatorio")
    public String citaId;

    @NotBlank(message = "El ID del cliente es obligatorio")
    public String clienteId;

    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 5, message = "La puntuación máxima es 5")
    public int puntuacion;

    public String comentario;

    public CalificacionRequest() {
    }

    public CalificacionRequest(String citaId, String clienteId, int puntuacion, String comentario) {
        this.citaId = citaId;
        this.clienteId = clienteId;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
    }
}
