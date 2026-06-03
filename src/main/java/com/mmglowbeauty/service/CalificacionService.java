package com.mmglowbeauty.service;

import com.mmglowbeauty.dto.CalificacionRequest;

public interface CalificacionService {
    void registrarCalificacion(CalificacionRequest request);
    void responderCalificacion(String id, String respuesta);
    double calcularPromedio(String empleadaId);
}
