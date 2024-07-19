package com.nttadta.movement_service.service.implement;

import com.nttadta.movement_service.constants.PathConstants;
import com.nttadta.movement_service.exception.ResourceNotFoundException;
import com.nttadta.movement_service.model.TipoMovimiento;
import com.nttadta.movement_service.repository.TipoMovimientoRepository;
import com.nttadta.movement_service.service.TipoMovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Class for account type service
 */
@Service
@RequiredArgsConstructor
public class TipoMovimientoServiceImpl implements TipoMovimientoService {

    private final TipoMovimientoRepository movementTypeRepository;


    /**
     * Method to query a movement type by id
     *
     * @param id Movement type identifier
     * @return Object with the movement type information
     */
    @Override
    public CompletableFuture<TipoMovimiento> searchMovementTypeById(Integer id) {
        return CompletableFuture.supplyAsync(() -> movementTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PathConstants.MOVEMENT_TYPE, "id", id)));
    }
}
