package com.nttadta.movement_service.service.implement;

import com.nttadta.movement_service.constants.PathConstants;
import com.nttadta.movement_service.exception.ResourceNotFoundException;
import com.nttadta.movement_service.model.TipoCuenta;
import com.nttadta.movement_service.repository.TipoCuentaRepository;
import com.nttadta.movement_service.service.TipoCuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Class for the service of account type
 */
@Service
@RequiredArgsConstructor
public class TipoCuentaServiceImpl implements TipoCuentaService {

    private final TipoCuentaRepository repository;


    /**
     * Method to query an account type by id
     *
     * @param id Account type identifier
     * @return Object with the account type information
     */
    @Override
    public CompletableFuture<TipoCuenta> searchAccountTypeById(Integer id) {
        return CompletableFuture.supplyAsync(() -> repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PathConstants.ACCOUNT_TYPE, "id", id)));

    }
}
