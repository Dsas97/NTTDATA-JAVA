package com.nttadta.person_service.repository;

import com.nttadta.person_service.model.Persona;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends CrudRepository<Persona, Long> {
    <T> T findById(Long id, Class<T> type);
    @Query("SELECT p FROM Persona p")
    <T> List<T> findAll(Class<T> type);
}
