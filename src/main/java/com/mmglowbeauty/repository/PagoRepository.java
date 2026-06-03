package com.mmglowbeauty.repository;

import com.mmglowbeauty.model.Pago;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends MongoRepository<Pago, String> {
    Optional<Pago> findByCitaId(String citaId);
    List<Pago> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}
