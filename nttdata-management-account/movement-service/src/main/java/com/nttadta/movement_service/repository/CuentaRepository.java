package com.nttadta.movement_service.repository;

import com.nttadta.movement_service.model.Cuenta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository extends CrudRepository<Cuenta, Long> {

    <T> T findById(Long id, Class<T> type);
    @Query("SELECT cu FROM Cuenta cu")
    <T> List<T> findAll(Class<T> type);

    Boolean existsByCliente_Id(Long id);
}
