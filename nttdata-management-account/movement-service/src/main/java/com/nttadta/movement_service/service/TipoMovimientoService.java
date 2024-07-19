package com.nttadta.movement_service.service;

import com.nttadta.movement_service.model.TipoMovimiento;

import java.util.concurrent.CompletableFuture;

public interface TipoMovimientoService {
    CompletableFuture<TipoMovimiento> searchMovementTypeById(Integer id);
}
