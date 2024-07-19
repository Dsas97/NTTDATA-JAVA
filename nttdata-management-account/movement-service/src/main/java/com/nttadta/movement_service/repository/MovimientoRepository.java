package com.nttadta.movement_service.repository;

import com.nttadta.movement_service.model.Cuenta;
import com.nttadta.movement_service.model.Movimiento;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends CrudRepository<Movimiento, Long> {
    <T> T findById(Long id, Class<T> type);

    <T> List<T> findByCuentaId(Long accountId, Class<T> type);

    <T> List<T> findByCuentaId(Long accountId, Class<T> type, Pageable pageable);

    Long countByCuentaId(Long accountId);

    @Query("SELECT mo FROM Movimiento mo")
    <T> List<T> findAll(Class<T> type);

    @Query("SELECT mo FROM Movimiento mo WHERE mo.cuenta = :cuenta ORDER BY mo.fecha DESC LIMIT 1")
    Movimiento findLastMovimientoByCuenta(@Param("cuenta") Cuenta cuenta);

    <T> List<T> findByCuentaIdAndFechaBetweenAndEstado(Long accountId, LocalDateTime startDate, LocalDateTime endDate, Boolean state, Class<T> type, Pageable pageable);

    Long countByCuentaIdAndFechaBetweenAndEstado(Long accountId, LocalDateTime startDate, LocalDateTime endDate, Boolean state);
}
