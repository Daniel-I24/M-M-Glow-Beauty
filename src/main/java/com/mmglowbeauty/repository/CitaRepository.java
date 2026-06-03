package com.mmglowbeauty.repository;

import com.mmglowbeauty.model.Cita;
import com.mmglowbeauty.model.enums.EstadoCita;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends MongoRepository<Cita, String> {
    List<Cita> findByEmpleadaId(String empleadaId);
    List<Cita> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Cita> findByEmpleadaIdAndEstado(String empleadaId, EstadoCita estado);
}
