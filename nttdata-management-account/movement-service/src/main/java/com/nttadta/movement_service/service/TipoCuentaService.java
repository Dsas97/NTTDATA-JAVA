package com.nttadta.movement_service.service;

import com.nttadta.movement_service.model.TipoCuenta;

import java.util.concurrent.CompletableFuture;

public interface TipoCuentaService {
    CompletableFuture<TipoCuenta> searchAccountTypeById(Integer id);
}
