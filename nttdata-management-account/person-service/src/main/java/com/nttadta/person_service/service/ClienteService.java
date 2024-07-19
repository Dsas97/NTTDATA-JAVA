package com.nttadta.person_service.service;

import com.nttadta.person_service.dto.request.ClienteDto;
import com.nttadta.person_service.dto.request.ClientePatchDto;
import com.nttadta.person_service.dto.response.IClienteResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ClienteService {
    CompletableFuture<IClienteResponse> create(ClienteDto clienteDto);

    CompletableFuture<IClienteResponse> update(Long id, ClienteDto clienteDto);

    CompletableFuture<IClienteResponse> updateByFields(Long id, ClientePatchDto clienteDto);

    CompletableFuture<Void> deleteClientById(Long id);

    CompletableFuture<IClienteResponse> searchClientById(Long id);

    CompletableFuture<List<IClienteResponse>> searchClients();
}
