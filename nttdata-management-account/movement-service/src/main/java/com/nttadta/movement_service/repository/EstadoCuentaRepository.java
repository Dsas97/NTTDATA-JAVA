package com.nttadta.movement_service.repository;

import com.nttadta.movement_service.model.EstadoCuenta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoCuentaRepository extends CrudRepository<EstadoCuenta, Integer> {
}
