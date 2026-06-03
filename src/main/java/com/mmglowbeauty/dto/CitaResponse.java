package com.mmglowbeauty.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CitaResponse {

    public String id;
    public String clienteNombre;
    public String empleadaNombre;
    public List<String> serviciosNombres;
    public LocalDateTime fechaHora;
    public String estado;
    public int duracionMin;

    public CitaResponse() {
    }

    public CitaResponse(String id, String clienteNombre, String empleadaNombre, List<String> serviciosNombres,
                        LocalDateTime fechaHora, String estado, int duracionMin) {
        this.id = id;
        this.clienteNombre = clienteNombre;
        this.empleadaNombre = empleadaNombre;
        this.serviciosNombres = serviciosNombres;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.duracionMin = duracionMin;
    }
}
