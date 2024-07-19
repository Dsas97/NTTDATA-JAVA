package com.nttadta.person_service.repository;

import com.nttadta.person_service.model.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    <T> T findById(Long id, Class<T> type);

    @Query("SELECT c FROM Cliente c")
    <T> List<T> findAll(Class<T> type);

}
