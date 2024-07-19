package com.nttadta.movement_service.service;

import com.nttadta.movement_service.dto.request.MovimientoDto;
import com.nttadta.movement_service.dto.response.IMovimientoResponse;
import com.nttadta.movement_service.utils.Pagination;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MovimientoService {
    CompletableFuture<IMovimientoResponse> create(MovimientoDto movimientoDto);
    CompletableFuture<Object> searchMovementById(Long id, Class<?> clase);
    CompletableFuture<List<IMovimientoResponse>> searchMovementsByAccount(Long accountId);
    CompletableFuture<Pagination<IMovimientoResponse>> searchMovementsByAccountAndPagination(Long accountId, Integer page, Integer recordsByPage, String sortBy, String address);
    CompletableFuture<Pagination<IMovimientoResponse>> searchMovementsByAccountAndBetweenDates(Long accountId, LocalDateTime startDate, LocalDateTime endDate, Integer page, Integer recordsByPage, String sortBy, String address);
}
