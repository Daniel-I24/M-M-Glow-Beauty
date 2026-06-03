package com.mmglowbeauty.service.impl;

import com.mmglowbeauty.dto.CitaRequest;
import com.mmglowbeauty.dto.CitaResponse;
import com.mmglowbeauty.exception.RecursoNoEncontradoException;
import com.mmglowbeauty.exception.SlotNoDisponibleException;
import com.mmglowbeauty.model.Cita;
import com.mmglowbeauty.model.Servicio;
import com.mmglowbeauty.model.Usuario;
import com.mmglowbeauty.model.enums.EstadoCita;
import com.mmglowbeauty.repository.CitaRepository;
import com.mmglowbeauty.repository.ServicioRepository;
import com.mmglowbeauty.repository.UsuarioRepository;
import com.mmglowbeauty.service.CitaService;
import com.mmglowbeauty.service.NotificacionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CitaServiceImpl implements CitaService {

    private static final Logger log = LoggerFactory.getLogger(CitaServiceImpl.class);

    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ServicioRepository servicioRepository;
    private final NotificacionStrategy notificacionStrategy;

    public CitaServiceImpl(CitaRepository citaRepository, UsuarioRepository usuarioRepository,
                           ServicioRepository servicioRepository, NotificacionStrategy notificacionStrategy) {
        this.citaRepository = citaRepository;
        this.usuarioRepository = usuarioRepository;
        this.servicioRepository = servicioRepository;
        this.notificacionStrategy = notificacionStrategy;
    }

    @Override
    public CitaResponse crearCita(CitaRequest request) {
        log.info("Creando cita para cliente: {} con empleada: {}", request.clienteId, request.empleadaId);

        if (!verificarDisponibilidad(request.empleadaId, request.fechaHora)) {
            throw new SlotNoDisponibleException("El horario solicitado no está disponible");
        }

        int duracionTotal = request.servicioIds.stream()
                .map(id -> servicioRepository.findById(id)
                        .orElseThrow(() -> new RecursoNoEncontradoException("Servicio no encontrado: " + id)))
                .mapToInt(Servicio::getDuracionMin)
                .sum();

        Cita cita = new Cita();
        cita.setClienteId(request.clienteId);
        cita.setEmpleadaId(request.empleadaId);
        cita.setServicioIds(request.servicioIds);
        cita.setFechaHora(request.fechaHora);
        cita.setNotas(request.notas);
        cita.setEstado(EstadoCita.PENDIENTE);
        cita.setDuracionMin(duracionTotal);
        cita.setProductosConsumidosIds(new ArrayList<>());

        citaRepository.save(cita);
        log.info("Cita creada exitosamente con id: {}", cita.getId());

        return buildCitaResponse(cita);
    }

    @Override
    public CitaResponse actualizarCita(String id, CitaRequest request) {
        log.info("Actualizando cita con id: {}", id);

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada con id: " + id));

        if (!cita.getEmpleadaId().equals(request.empleadaId) || !cita.getFechaHora().equals(request.fechaHora)) {
            if (!verificarDisponibilidad(request.empleadaId, request.fechaHora)) {
                throw new SlotNoDisponibleException("El horario solicitado no está disponible");
            }
        }

        int duracionTotal = request.servicioIds.stream()
                .map(servicioId -> servicioRepository.findById(servicioId)
                        .orElseThrow(() -> new RecursoNoEncontradoException("Servicio no encontrado: " + servicioId)))
                .mapToInt(Servicio::getDuracionMin)
                .sum();

        cita.setClienteId(request.clienteId);
        cita.setEmpleadaId(request.empleadaId);
        cita.setServicioIds(request.servicioIds);
        cita.setFechaHora(request.fechaHora);
        cita.setNotas(request.notas);
        cita.setDuracionMin(duracionTotal);

        citaRepository.save(cita);
        log.info("Cita actualizada exitosamente: {}", id);

        return buildCitaResponse(cita);
    }

    @Override
    public void cancelarCita(String id) {
        log.info("Cancelando cita con id: {}", id);

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada con id: " + id));

        cita.setEstado(EstadoCita.CANCELADA);
        citaRepository.save(cita);

        Usuario cliente = usuarioRepository.findById(cita.getClienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));

        String mensaje = String.format("Su cita del %s ha sido cancelada.", cita.getFechaHora());
        notificacionStrategy.enviar(cliente.getCorreo(), mensaje);

        log.info("Cita cancelada exitosamente: {}", id);
    }

    @Override
    public void confirmarCita(String id) {
        log.info("Confirmando cita con id: {}", id);

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada con id: " + id));

        cita.setEstado(EstadoCita.CONFIRMADA);
        citaRepository.save(cita);

        Usuario cliente = usuarioRepository.findById(cita.getClienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));

        String mensaje = String.format("Su cita del %s ha sido confirmada. ¡Le esperamos!", cita.getFechaHora());
        notificacionStrategy.enviar(cliente.getCorreo(), mensaje);

        log.info("Cita confirmada exitosamente: {}", id);
    }

    @Override
    public List<CitaResponse> listarCitas() {
        log.info("Listando citas");
        return citaRepository.findAll().stream()
                .map(this::buildCitaResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaResponse> listarPorEmpleada(String empleadaId) {
        log.info("Listando citas para empleada: {}", empleadaId);
        return citaRepository.findByEmpleadaId(empleadaId).stream()
                .map(this::buildCitaResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean verificarDisponibilidad(String empleadaId, LocalDateTime fechaHora) {
        List<Cita> citasConfirmadas = citaRepository.findByEmpleadaIdAndEstado(empleadaId, EstadoCita.CONFIRMADA);

        for (Cita cita : citasConfirmadas) {
            LocalDateTime inicioCita = cita.getFechaHora();
            LocalDateTime finCita = inicioCita.plusMinutes(cita.getDuracionMin());

            LocalDateTime inicioSolicitado = fechaHora.minusMinutes(30);
            LocalDateTime finSolicitado = fechaHora.plusMinutes(30);

            if ((fechaHora.isAfter(inicioCita) && fechaHora.isBefore(finCita)) ||
                (inicioSolicitado.isBefore(finCita) && finSolicitado.isAfter(inicioCita))) {
                return false;
            }
        }

        return true;
    }

    private CitaResponse buildCitaResponse(Cita cita) {
        Usuario cliente = usuarioRepository.findById(cita.getClienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));

        Usuario empleada = usuarioRepository.findById(cita.getEmpleadaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Empleada no encontrada"));

        List<String> serviciosNombres = cita.getServicioIds().stream()
                .map(id -> servicioRepository.findById(id)
                        .map(Servicio::getNombre)
                        .orElse("Servicio no encontrado"))
                .collect(Collectors.toList());

        return new CitaResponse(
                cita.getId(),
                cliente.getNombre(),
                empleada.getNombre(),
                serviciosNombres,
                cita.getFechaHora(),
                cita.getEstado().name(),
                cita.getDuracionMin()
        );
    }
}
