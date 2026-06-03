package com.mmglowbeauty.repository;

import com.mmglowbeauty.model.Promocion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromocionRepository extends MongoRepository<Promocion, String> {
    List<Promocion> findByActivaTrue();
}
