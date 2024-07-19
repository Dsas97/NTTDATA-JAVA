package com.nttadta.movement_service.controller;

import com.nttadta.movement_service.constants.PathConstants;
import com.nttadta.movement_service.dto.request.MovimientoDto;
import com.nttadta.movement_service.dto.response.IMovimientoResponse;
import com.nttadta.movement_service.exception.BadRequestException;
import com.nttadta.movement_service.service.implement.MovimientoServiceImpl;
import com.nttadta.movement_service.utils.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(PathConstants.MOVEMENT)
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoServiceImpl movementServiceImpl;


    /**
     * Method to create a movement
     *
     * @param movimientoDto Object with the movement information
     * @return Object with the information of the created movement
     */
    @PostMapping
    public CompletableFuture<ResponseEntity<IMovimientoResponse>> creatMovement(@RequestBody @Valid MovimientoDto movimientoDto) {
        return movementServiceImpl.create(movimientoDto)
                .thenApply(ResponseEntity::ok);
    }


    /**
     * Method to consult a movement by id
     *
     * @param id Movement identifier
     * @return Object with the movement information
     */
    @GetMapping(PathConstants.ID_PARAM)
    public CompletableFuture<ResponseEntity<IMovimientoResponse>> searchMovementById(@PathVariable Long id) {
        return movementServiceImpl.searchMovementById(id, IMovimientoResponse.class)
                .thenApply(result -> ResponseEntity.ok((IMovimientoResponse) result))
                .exceptionally(this::handleException);
    }


    /**
     * Method to consult the movements of an account
     *
     * @param id Account identifier
     * @return List of account movements
     */
    @GetMapping(PathConstants.ACCOUNT + PathConstants.ID_PARAM)
    public CompletableFuture<ResponseEntity<List<IMovimientoResponse>>> searchMovementsByAccountId(@PathVariable Long id) {
        return movementServiceImpl.searchMovementsByAccount(id)
                .thenApply(ResponseEntity::ok)
                .exceptionally(this::handleException);
    }

    /**
     * Method to consult the movements of an account with pagination
     *
     * @param accountId Account identifier
     * @param startDate Start date
     * @param endDate End date
     * @param page Page to consult
     * @param recordsPerPage Records per page (page size)
     * @param sortBy Sort by entity field
     * @param address Address (ASC or DESC)
     * @return List of account transactions with pagination
     */
    @GetMapping(PathConstants.ACCOUNT_STATE)
    public CompletableFuture<ResponseEntity<Pagination<IMovimientoResponse>>> consultarMovimientoPorIdCuenta(
            @RequestParam Long accountId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam Integer page,
            @RequestParam Integer recordsPerPage,
            @RequestParam String sortBy,
            @RequestParam String address) {
        if (startDate != null && endDate != null) {
            // If both dates are provided, you can use them to filter movements by date range
            return movementServiceImpl.searchMovementsByAccountAndBetweenDates(accountId, startDate, endDate, page, recordsPerPage, sortBy, address)
                    .thenApply(ResponseEntity::ok)
                    .exceptionally(this::handleException);
        } else {
            // If both dates are not provided, you can simply check the movements by account
            return movementServiceImpl.searchMovementsByAccountAndPagination(accountId, page, recordsPerPage, sortBy, address)
                    .thenApply(ResponseEntity::ok)
                    .exceptionally(this::handleException);
        }
    }

    /**
     * Method to handle exceptions
     *
     * @param ex Exception
     * @param <T> Data type
     * @return Custom exception
     */
    private <T> ResponseEntity<T> handleException(Throwable ex) {
        throw new BadRequestException(ex.getMessage());
    }
}
