package com.mmglowbeauty.service.impl;

import com.mmglowbeauty.dto.CalificacionRequest;
import com.mmglowbeauty.exception.RecursoNoEncontradoException;
import com.mmglowbeauty.model.Calificacion;
import com.mmglowbeauty.model.Cita;
import com.mmglowbeauty.model.enums.EstadoCita;
import com.mmglowbeauty.repository.CalificacionRepository;
import com.mmglowbeauty.repository.CitaRepository;
import com.mmglowbeauty.service.CalificacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CalificacionServiceImpl implements CalificacionService {

    private static final Logger log = LoggerFactory.getLogger(CalificacionServiceImpl.class);

    private final CalificacionRepository calificacionRepository;
    private final CitaRepository citaRepository;

    public CalificacionServiceImpl(CalificacionRepository calificacionRepository, CitaRepository citaRepository) {
        this.calificacionRepository = calificacionRepository;
        this.citaRepository = citaRepository;
    }

    @Override
    public void registrarCalificacion(CalificacionRequest request) {
        log.info("Registrando calificación para cita: {}", request.citaId);

        Cita cita = citaRepository.findById(request.citaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada con id: " + request.citaId));

        if (cita.getEstado() != EstadoCita.COMPLETADA) {
            throw new IllegalStateException("Solo se pueden calificar citas completadas");
        }

        if (calificacionRepository.findByCitaId(request.citaId).isPresent()) {
            throw new IllegalStateException("Esta cita ya ha sido calificada");
        }

        Calificacion calificacion = new Calificacion();
        calificacion.setCitaId(request.citaId);
        calificacion.setClienteId(request.clienteId);
        calificacion.setPuntuacion(request.puntuacion);
        calificacion.setComentario(request.comentario);
        calificacion.setFecha(LocalDate.now());

        calificacionRepository.save(calificacion);
        log.info("Calificación registrada exitosamente: {}", calificacion.getId());
    }

    @Override
    public void responderCalificacion(String id, String respuesta) {
        log.info("Respondiendo calificación con id: {}", id);

        Calificacion calificacion = calificacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Calificación no encontrada con id: " + id));

        calificacion.setRespuesta(respuesta);
        calificacionRepository.save(calificacion);
        log.info("Respuesta registrada exitosamente para calificación: {}", id);
    }

    @Override
    public double calcularPromedio(String empleadaId) {
        log.info("Calculando promedio de calificaciones para empleada: {}", empleadaId);

        List<Cita> citas = citaRepository.findByEmpleadaId(empleadaId);
        List<String> citaIds = citas.stream().map(Cita::getId).toList();

        if (citaIds.isEmpty()) {
            return 0.0;
        }

        List<Calificacion> calificaciones = calificacionRepository.findAll().stream()
                .filter(c -> citaIds.contains(c.getCitaId()))
                .toList();

        if (calificaciones.isEmpty()) {
            return 0.0;
        }

        double suma = calificaciones.stream()
                .mapToInt(Calificacion::getPuntuacion)
                .sum();

        return suma / calificaciones.size();
    }
}
