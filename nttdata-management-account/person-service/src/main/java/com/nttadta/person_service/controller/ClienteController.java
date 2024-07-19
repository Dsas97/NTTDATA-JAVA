package com.nttadta.person_service.controller;

import com.nttadta.person_service.constants.PathConstants;
import com.nttadta.person_service.dto.request.ClienteDto;
import com.nttadta.person_service.dto.request.ClientePatchDto;
import com.nttadta.person_service.dto.response.IClienteResponse;
import com.nttadta.person_service.exception.BadRequestException;
import com.nttadta.person_service.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(PathConstants.CLIENT)
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clientService;


    /**
     * Method to create a client
     *
     * @param clienteDto Object with the customer information
     * @return Object with the information of the created customer
     */
    @PostMapping
    public CompletableFuture<ResponseEntity<IClienteResponse>> createClient(@RequestBody @Valid ClienteDto clienteDto) {
        return clientService.create(clienteDto).thenApply(ResponseEntity::ok)
                .exceptionally(this::handleException);
    }


    /**
     * Method to update a client
     *
     * @param id Client identifier
     * @param clienteDto Object with the customer information
     * @return Object with the updated customer information
     */
    @PutMapping(PathConstants.ID_PARAM)
    public CompletableFuture<ResponseEntity<IClienteResponse>> updateClient(@PathVariable Long id, @RequestBody ClienteDto clienteDto) {
        return clientService.update(id, clienteDto).thenApply(ResponseEntity::ok)
                .exceptionally(this::handleException);
    }


    /**
     * Method to update a customer by fields
     *
     * @param id Client identifier
     * @param clienteDto Object with the customer information
     * @return Object with the updated customer information
     */
    @PatchMapping(PathConstants.ID_PARAM)
    public CompletableFuture<ResponseEntity<IClienteResponse>> updateClientByFields(@PathVariable Long id, @RequestBody ClientePatchDto clienteDto) {
        return clientService.updateByFields(id, clienteDto)
                .thenApply(ResponseEntity::ok)
                .exceptionally(this::handleException);
    }


    /**
     * Method to delete a customer
     *
     * @param id Client identifier
     * @return Empty response, no content()
     */
    @DeleteMapping(PathConstants.ID_PARAM)
    public CompletableFuture<ResponseEntity<Object>> deleteCliente(@PathVariable Long id) {
        return clientService.deleteClientById(id).thenApply(result -> ResponseEntity.noContent().build())
                .exceptionally(this::handleException);
    }


    /**
     * Method to query a client by id
     *
     * @param id Client identifier
     * @return Object with the customer information
     */
    @GetMapping(PathConstants.ID_PARAM)
    public CompletableFuture<ResponseEntity<IClienteResponse>> searchClientById(@PathVariable Long id) {
        return clientService.searchClientById(id).thenApply(ResponseEntity::ok)
                .exceptionally(this::handleException);
    }


    /**
     * Method to query all clients
     *
     * @return List of objects with customer information
     */
    @GetMapping
    public CompletableFuture<ResponseEntity<List<IClienteResponse>>> searchClients() {
        return clientService.searchClients().thenApply(ResponseEntity::ok)
                .exceptionally(this::handleException);
    }

    private <T> ResponseEntity<T> handleException(Throwable ex) {
        throw new BadRequestException(ex.getMessage());
    }
}
