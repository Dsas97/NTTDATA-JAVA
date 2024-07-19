package com.nttadta.movement_service.repository;

import com.nttadta.movement_service.model.TipoCuenta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoCuentaRepository extends CrudRepository<TipoCuenta, Integer> {
}
