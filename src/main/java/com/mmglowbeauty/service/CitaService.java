package com.mmglowbeauty.service;

import com.mmglowbeauty.dto.CitaRequest;
import com.mmglowbeauty.dto.CitaResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaService {
    CitaResponse crearCita(CitaRequest request);
    CitaResponse actualizarCita(String id, CitaRequest request);
    void cancelarCita(String id);
    void confirmarCita(String id);
    List<CitaResponse> listarCitas();
    List<CitaResponse> listarPorEmpleada(String empleadaId);
    boolean verificarDisponibilidad(String empleadaId, LocalDateTime fechaHora);
}
