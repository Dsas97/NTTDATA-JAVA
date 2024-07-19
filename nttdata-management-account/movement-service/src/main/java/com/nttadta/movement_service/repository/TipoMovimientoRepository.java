package com.nttadta.movement_service.repository;

import com.nttadta.movement_service.model.TipoMovimiento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoMovimientoRepository extends CrudRepository<TipoMovimiento, Integer> {
}
