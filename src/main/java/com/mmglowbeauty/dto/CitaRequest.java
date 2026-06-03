package com.mmglowbeauty.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class CitaRequest {

    @NotBlank(message = "El ID del cliente es obligatorio")
    public String clienteId;

    @NotBlank(message = "El ID de la empleada es obligatorio")
    public String empleadaId;

    @NotEmpty(message = "Debe seleccionar al menos un servicio")
    public List<String> servicioIds;

    @NotNull(message = "La fecha y hora son obligatorias")
    public LocalDateTime fechaHora;

    public String notas;

    public CitaRequest() {
    }

    public CitaRequest(String clienteId, String empleadaId, List<String> servicioIds,
                       LocalDateTime fechaHora, String notas) {
        this.clienteId = clienteId;
        this.empleadaId = empleadaId;
        this.servicioIds = servicioIds;
        this.fechaHora = fechaHora;
        this.notas = notas;
    }
}
