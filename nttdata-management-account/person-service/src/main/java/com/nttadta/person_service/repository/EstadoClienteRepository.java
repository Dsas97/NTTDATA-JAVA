package com.nttadta.person_service.repository;

import com.nttadta.person_service.model.EstadoCliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EstadoClienteRepository extends CrudRepository<EstadoCliente, Integer> {
}
