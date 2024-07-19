package com.nttadta.person_service.service.implement;

import com.nttadta.person_service.constants.PathConstants;
import com.nttadta.person_service.exception.ResourceNotFoundException;
import com.nttadta.person_service.model.EstadoCliente;
import com.nttadta.person_service.repository.EstadoClienteRepository;
import com.nttadta.person_service.service.EstadoClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Class for the service of the client state
 */
@Service
@RequiredArgsConstructor
public class EstadoClienteServiceImpl implements EstadoClienteService {
    private final EstadoClienteRepository clientStateRepository;

    @Override
    public CompletableFuture<EstadoCliente> searchClientStateById(Integer id) {
        return CompletableFuture.supplyAsync(() -> clientStateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PathConstants.CLIENT_STATE, "id", id)));
    }
}
