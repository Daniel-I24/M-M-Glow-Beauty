package com.mmglowbeauty.repository;

import com.mmglowbeauty.model.Recordatorio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordatorioRepository extends MongoRepository<Recordatorio, String> {
    List<Recordatorio> findByCitaId(String citaId);
    boolean existsByCitaIdAndCanal(String citaId, String canal);
}

