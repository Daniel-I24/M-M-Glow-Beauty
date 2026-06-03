package com.mmglowbeauty.service;

import com.mmglowbeauty.dto.CitaRequest;
import com.mmglowbeauty.dto.CitaResponse;
import com.mmglowbeauty.exception.RecursoNoEncontradoException;
import com.mmglowbeauty.exception.SlotNoDisponibleException;
import com.mmglowbeauty.model.Cita;
import com.mmglowbeauty.model.Cliente;
import com.mmglowbeauty.model.Empleada;
import com.mmglowbeauty.model.Servicio;
import com.mmglowbeauty.model.enums.EstadoCita;
import com.mmglowbeauty.repository.CitaRepository;
import com.mmglowbeauty.repository.ServicioRepository;
import com.mmglowbeauty.repository.UsuarioRepository;
import com.mmglowbeauty.service.impl.CitaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitaServiceImplTest {

    @Mock
    private CitaRepository citaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ServicioRepository servicioRepository;

    @Mock
    private NotificacionStrategy notificacionStrategy;

    @InjectMocks
    private CitaServiceImpl citaService;

    private CitaRequest citaRequest;
    private Servicio servicio;
    private Cliente cliente;
    private Empleada empleada;

    @BeforeEach
    void setUp() {
        citaRequest = new CitaRequest();
        citaRequest.clienteId = "cliente123";
        citaRequest.empleadaId = "empleada123";
        citaRequest.servicioIds = List.of("servicio123");
        citaRequest.fechaHora = LocalDateTime.now().plusDays(1);
        citaRequest.notas = "Test";

        servicio = new Servicio();
        servicio.setId("servicio123");
        servicio.setNombre("Manicure");
        servicio.setDuracionMin(60);

        cliente = new Cliente();
        cliente.setId("cliente123");
        cliente.setNombre("María");
        cliente.setCorreo("maria@example.com");

        empleada = new Empleada();
        empleada.setId("empleada123");
        empleada.setNombre("Lucía");
    }

    @Test
    void testCrearCita_exitoso() {
        when(servicioRepository.findById("servicio123")).thenReturn(Optional.of(servicio));
        when(citaRepository.findByEmpleadaIdAndEstado("empleada123", EstadoCita.CONFIRMADA))
                .thenReturn(List.of());
        when(citaRepository.save(any(Cita.class))).thenAnswer(i -> i.getArguments()[0]);
        when(usuarioRepository.findById("cliente123")).thenReturn(Optional.of(cliente));
        when(usuarioRepository.findById("empleada123")).thenReturn(Optional.of(empleada));

        CitaResponse result = citaService.crearCita(citaRequest);

        assertNotNull(result);
        assertEquals("María", result.clienteNombre);
        verify(citaRepository, times(1)).save(any(Cita.class));
    }

    @Test
    void testCrearCita_slotNoDisponible() {
        Cita citaExistente = new Cita();
        citaExistente.setFechaHora(citaRequest.fechaHora);
        citaExistente.setDuracionMin(60);
        citaExistente.setEstado(EstadoCita.CONFIRMADA);

        when(citaRepository.findByEmpleadaIdAndEstado("empleada123", EstadoCita.CONFIRMADA))
                .thenReturn(List.of(citaExistente));

        assertThrows(SlotNoDisponibleException.class, () -> {
            citaService.crearCita(citaRequest);
        });
    }

    @Test
    void testVerificarDisponibilidad_libre() {
        when(citaRepository.findByEmpleadaIdAndEstado("empleada123", EstadoCita.CONFIRMADA))
                .thenReturn(List.of());

        boolean result = citaService.verificarDisponibilidad("empleada123", LocalDateTime.now().plusDays(1));

        assertTrue(result);
    }

    @Test
    void testCancelarCita_exitoso() {
        Cita cita = new Cita();
        cita.setId("cita123");
        cita.setClienteId("cliente123");
        cita.setFechaHora(LocalDateTime.now().plusDays(1));
        cita.setEstado(EstadoCita.PENDIENTE);

        when(citaRepository.findById("cita123")).thenReturn(Optional.of(cita));
        when(usuarioRepository.findById("cliente123")).thenReturn(Optional.of(cliente));
        when(citaRepository.save(any(Cita.class))).thenReturn(cita);

        citaService.cancelarCita("cita123");

        assertEquals(EstadoCita.CANCELADA, cita.getEstado());
        verify(notificacionStrategy, times(1)).enviar(anyString(), anyString());
    }
}
