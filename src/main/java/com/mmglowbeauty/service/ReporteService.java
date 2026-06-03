package com.mmglowbeauty.service;

import com.mmglowbeauty.dto.CitaResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReporteService {
    Map<String, Object> ingresosPorRango(LocalDate inicio, LocalDate fin);
    List<Map<String, Object>> serviciosMasSolicitados();
    List<CitaResponse> historialCitas(LocalDate inicio, LocalDate fin);
}
