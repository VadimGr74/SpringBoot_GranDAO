package org.example.spring_recuperacion.repository;

import org.bson.types.ObjectId;
import org.example.spring_recuperacion.modelo.Devolucione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevolucioneRepository extends MongoRepository<Devolucione, String> {
    Devolucione findDevolucioneById(Integer id);
    Integer deleteDevolucioneById(Integer id);
}
