package com.nttadta.movement_service.service;

import com.nttadta.movement_service.model.EstadoCuenta;

import java.util.concurrent.CompletableFuture;

public interface EstadoCuentaService {
    CompletableFuture<EstadoCuenta> searchAccountStateById(Integer id);
}
