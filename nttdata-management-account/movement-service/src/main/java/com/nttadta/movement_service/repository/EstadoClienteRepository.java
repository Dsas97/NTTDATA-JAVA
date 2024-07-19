package com.nttadta.movement_service.repository;

import com.nttadta.movement_service.model.EstadoCliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoClienteRepository extends CrudRepository<EstadoCliente, Integer> {
}
