package com.nttadta.movement_service.controller;

import com.nttadta.movement_service.constants.PathConstants;
import com.nttadta.movement_service.dto.request.CuentaDto;
import com.nttadta.movement_service.dto.request.CuentaPatchDto;
import com.nttadta.movement_service.dto.response.ICuentaResponse;
import com.nttadta.movement_service.model.Cuenta;
import com.nttadta.movement_service.service.implement.CuentaServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(PathConstants.ACCOUNT)
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaServiceImpl accountServiceImpl;


    /**
     * Method to create an account
     *
     * @param cuentaDto Object with the account information
     * @return Object with the information of the created account
     */
    @PostMapping
    public CompletableFuture<ResponseEntity<Cuenta>> createAccoount(@RequestBody @Valid CuentaDto cuentaDto) {
        return accountServiceImpl.create(cuentaDto).thenApply(ResponseEntity::ok);
    }


    /**
     * Method to update an account
     *
     * @param id Account identifier
     * @param clienteDto Object with the account information
     * @return Object with updated account information
     */
    @PutMapping(PathConstants.ID_PARAM)
    public CompletableFuture<ResponseEntity<Cuenta>> updateAccount(@PathVariable Long id, @RequestBody @Valid CuentaDto clienteDto) {
        return accountServiceImpl.update(id, clienteDto).thenApply(ResponseEntity::ok);
    }


    /**
     * Method to update an account by fields
     *
     * @param id Account identifier
     * @param clienteDto Object with the account information
     * @return Object with updated account information
     */
    @PatchMapping(PathConstants.ID_PARAM)
    public CompletableFuture<ResponseEntity<ICuentaResponse>> updateAccountByFields(@PathVariable Long id, @RequestBody CuentaPatchDto clienteDto) {
        return accountServiceImpl.updateByFields(id, clienteDto)
                .thenApply(ResponseEntity::ok);
    }

    /**
     * Method to check an account by id
     *
     * @param id Account identifier
     * @return Object with the account information
     */
    @GetMapping(PathConstants.ID_PARAM)
    public CompletableFuture<ResponseEntity<ICuentaResponse>> searchAccountById(@PathVariable Long id) {
        return accountServiceImpl.searchAccountById(id, ICuentaResponse.class).thenApply(result -> ResponseEntity.ok((ICuentaResponse) result));
    }

    /**
     * Method to consult accounts
     *
     * @return List of objects with account information
     */
    @GetMapping
    public CompletableFuture<ResponseEntity<List<ICuentaResponse>>> searchAccounts() {
        return accountServiceImpl.searchAccounts().thenApply(ResponseEntity::ok);
    }
}
