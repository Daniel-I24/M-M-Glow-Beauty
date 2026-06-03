package com.mmglowbeauty.service.impl;

import com.mmglowbeauty.dto.CitaResponse;
import com.mmglowbeauty.model.Cita;
import com.mmglowbeauty.model.Pago;
import com.mmglowbeauty.repository.CitaRepository;
import com.mmglowbeauty.repository.PagoRepository;
import com.mmglowbeauty.service.CitaService;
import com.mmglowbeauty.service.ReporteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReporteServiceImpl implements ReporteService {

    private static final Logger log = LoggerFactory.getLogger(ReporteServiceImpl.class);

    private final PagoRepository pagoRepository;
    private final CitaRepository citaRepository;
    private final CitaService citaService;

    public ReporteServiceImpl(PagoRepository pagoRepository, CitaRepository citaRepository, CitaService citaService) {
        this.pagoRepository = pagoRepository;
        this.citaRepository = citaRepository;
        this.citaService = citaService;
    }

    @Override
    public Map<String, Object> ingresosPorRango(LocalDate inicio, LocalDate fin) {
        log.info("Generando reporte de ingresos: {} - {}", inicio, fin);

        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime finDateTime = fin.atTime(LocalTime.MAX);

        List<Pago> pagos = pagoRepository.findByFechaBetween(inicioDateTime, finDateTime);

        double totalIngresos = pagos.stream()
                .mapToDouble(Pago::getMonto)
                .sum();

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("fechaInicio", inicio);
        resultado.put("fechaFin", fin);
        resultado.put("totalIngresos", totalIngresos);
        resultado.put("cantidadPagos", pagos.size());
        resultado.put("pagos", pagos);

        return resultado;
    }

    @Override
    public List<Map<String, Object>> serviciosMasSolicitados() {
        log.info("Generando reporte de servicios populares");

        List<Cita> todasCitas = citaRepository.findAll();

        Map<String, Long> conteoServicios = todasCitas.stream()
                .flatMap(cita -> cita.getServicioIds().stream())
                .collect(Collectors.groupingBy(servicioId -> servicioId, Collectors.counting()));

        return conteoServicios.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(entry -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("servicioId", entry.getKey());
                    item.put("cantidad", entry.getValue());
                    return item;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaResponse> historialCitas(LocalDate inicio, LocalDate fin) {
        log.info("Generando historial de citas: {} - {}", inicio, fin);

        return citaService.listarCitas().stream()
                .filter(cita -> {
                    LocalDate fechaCita = cita.fechaHora.toLocalDate();
                    return !fechaCita.isBefore(inicio) && !fechaCita.isAfter(fin);
                })
                .collect(Collectors.toList());
    }
}
