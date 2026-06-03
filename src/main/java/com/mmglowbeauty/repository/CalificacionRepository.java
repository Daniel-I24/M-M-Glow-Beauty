package com.mmglowbeauty.repository;

import com.mmglowbeauty.model.Calificacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CalificacionRepository extends MongoRepository<Calificacion, String> {
    Optional<Calificacion> findByCitaId(String citaId);
}
