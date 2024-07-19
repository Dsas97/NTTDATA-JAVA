package com.nttadta.movement_service.repository;

import com.nttadta.movement_service.model.Persona;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends CrudRepository<Persona, Long> {
}
