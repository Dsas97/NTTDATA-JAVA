package com.nttadta.movement_service.service;

import com.nttadta.movement_service.dto.request.CuentaDto;
import com.nttadta.movement_service.dto.request.CuentaPatchDto;
import com.nttadta.movement_service.dto.response.ICuentaResponse;
import com.nttadta.movement_service.model.Cuenta;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CuentaService {
    CompletableFuture<Cuenta> create(CuentaDto cuentaDto);
    CompletableFuture<Cuenta> update(Long id, CuentaDto cuentaDto);
    CompletableFuture<ICuentaResponse> updateByFields(Long id, CuentaPatchDto cuentaDto);
    CompletableFuture<Object> searchAccountById(Long id, Class<?> clase);
    CompletableFuture<List<ICuentaResponse>> searchAccounts();
}
