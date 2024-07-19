package com.nttadta.person_service.service;

import com.nttadta.person_service.model.EstadoCliente;

import java.util.concurrent.CompletableFuture;

public interface EstadoClienteService {
    CompletableFuture<EstadoCliente> searchClientStateById(Integer id);
}
