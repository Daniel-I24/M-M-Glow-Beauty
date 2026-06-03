package com.mmglowbeauty.service;

import com.mmglowbeauty.dto.CalificacionRequest;
import com.mmglowbeauty.model.Calificacion;
import com.mmglowbeauty.model.Cita;
import com.mmglowbeauty.model.enums.EstadoCita;
import com.mmglowbeauty.repository.CalificacionRepository;
import com.mmglowbeauty.repository.CitaRepository;
import com.mmglowbeauty.service.impl.CalificacionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalificacionServiceImplTest {

    @Mock
    private CalificacionRepository calificacionRepository;

    @Mock
    private CitaRepository citaRepository;

    @InjectMocks
    private CalificacionServiceImpl calificacionService;

    private CalificacionRequest calificacionRequest;
    private Cita cita;

    @BeforeEach
    void setUp() {
        calificacionRequest = new CalificacionRequest();
        calificacionRequest.citaId = "cita123";
        calificacionRequest.clienteId = "cliente123";
        calificacionRequest.puntuacion = 5;
        calificacionRequest.comentario = "Excelente servicio";

        cita = new Cita();
        cita.setId("cita123");
        cita.setEstado(EstadoCita.COMPLETADA);
        cita.setEmpleadaId("empleada123");
    }

    @Test
    void testRegistrarCalificacion_exitoso() {
        when(citaRepository.findById("cita123")).thenReturn(Optional.of(cita));
        when(calificacionRepository.findByCitaId("cita123")).thenReturn(Optional.empty());
        when(calificacionRepository.save(any(Calificacion.class))).thenAnswer(i -> i.getArguments()[0]);

        assertDoesNotThrow(() -> calificacionService.registrarCalificacion(calificacionRequest));

        verify(calificacionRepository, times(1)).save(any(Calificacion.class));
    }

    @Test
    void testRegistrarCalificacion_citaNoCompletada() {
        cita.setEstado(EstadoCita.CONFIRMADA);
        when(citaRepository.findById("cita123")).thenReturn(Optional.of(cita));

        assertThrows(IllegalStateException.class, () -> {
            calificacionService.registrarCalificacion(calificacionRequest);
        });

        verify(calificacionRepository, never()).save(any());
    }

    @Test
    void testCalcularPromedio_retornaPromedioCorrecto() {
        Cita cita1 = new Cita();
        cita1.setId("cita1");
        Cita cita2 = new Cita();
        cita2.setId("cita2");
        Cita cita3 = new Cita();
        cita3.setId("cita3");

        Calificacion cal1 = new Calificacion();
        cal1.setCitaId("cita1");
        cal1.setPuntuacion(4);

        Calificacion cal2 = new Calificacion();
        cal2.setCitaId("cita2");
        cal2.setPuntuacion(5);

        Calificacion cal3 = new Calificacion();
        cal3.setCitaId("cita3");
        cal3.setPuntuacion(3);

        when(citaRepository.findByEmpleadaId("empleada123"))
                .thenReturn(List.of(cita1, cita2, cita3));
        when(calificacionRepository.findAll())
                .thenReturn(List.of(cal1, cal2, cal3));

        double promedio = calificacionService.calcularPromedio("empleada123");

        assertEquals(4.0, promedio, 0.01);
    }
}
