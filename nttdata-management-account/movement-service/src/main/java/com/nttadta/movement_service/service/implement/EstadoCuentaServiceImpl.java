package com.nttadta.movement_service.service.implement;

import com.nttadta.movement_service.constants.PathConstants;
import com.nttadta.movement_service.exception.ResourceNotFoundException;
import com.nttadta.movement_service.model.EstadoCuenta;
import com.nttadta.movement_service.repository.EstadoCuentaRepository;
import com.nttadta.movement_service.service.EstadoCuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Class for the service of account state
 */
@Service
@RequiredArgsConstructor
public class EstadoCuentaServiceImpl implements EstadoCuentaService {

    private final EstadoCuentaRepository accountStateRepository;


    /**
     * Method to consult an account statement by id
     *
     * @param id Statement identifier
     * @return Object with the account statement information
     */
    @Override
    public CompletableFuture<EstadoCuenta> searchAccountStateById(Integer id) {
        return CompletableFuture.supplyAsync(() -> accountStateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PathConstants.ACCOUNT_STATE, "id", id)));
    }
}
