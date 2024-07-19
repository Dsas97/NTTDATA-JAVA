package com.nttadta.movement_service.service.implement;

import com.nttadta.movement_service.dto.request.MovimientoDto;
import com.nttadta.movement_service.dto.response.IMovimientoResponse;
import com.nttadta.movement_service.enums.TipoMovimientoEnum;
import com.nttadta.movement_service.exception.BadRequestException;
import com.nttadta.movement_service.exception.ResourceNotFoundException;
import com.nttadta.movement_service.model.Cuenta;
import com.nttadta.movement_service.model.Movimiento;
import com.nttadta.movement_service.model.TipoMovimiento;
import com.nttadta.movement_service.repository.MovimientoRepository;
import com.nttadta.movement_service.service.CuentaService;
import com.nttadta.movement_service.service.MovimientoService;
import com.nttadta.movement_service.service.TipoMovimientoService;
import com.nttadta.movement_service.utils.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Class for the service of movement
 */
@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movementRepository;
    private final CuentaService accountService;
    private final TipoMovimientoService movementTypeService;


    /**
     * Method to create a movement
     *
     * @param movimientoDto Object with the movement information
     * @return Object with the information of the created movement
     */
    @Override
    public CompletableFuture<IMovimientoResponse> create(MovimientoDto movimientoDto) {
        return CompletableFuture.supplyAsync(() -> {
            Cuenta cuenta = (Cuenta) accountService.searchAccountById(movimientoDto.getIdCuenta(), Cuenta.class).join();
            TipoMovimiento tipoMovimiento = movementTypeService.searchMovementTypeById(movimientoDto.getIdTipoMovimiento()).join();
            if (cuenta == null)
                throw new ResourceNotFoundException("Cuenta", "id", movimientoDto.getIdCuenta());
            Movimiento lastMovement = movementRepository.findLastMovimientoByCuenta(cuenta);
            BigDecimal saldo = lastMovement != null ? lastMovement.getSaldo() : cuenta.getSaldoInicial();
            Boolean isIncome = TipoMovimientoEnum.isIncomeById(tipoMovimiento.getId());

            //Compares the balance in the account with that of the movement (movimientoDto.getValue()) if the movement is greater than what is in the account it will not allow the transaction if it is not income
            if (Boolean.FALSE.equals(isIncome) && saldo.compareTo(movimientoDto.getValor()) < 0)
                throw new BadRequestException("Saldo no disponible");

            Movimiento movimiento = new Movimiento();
            movimiento.setCuenta(cuenta);
            movimiento.setFecha(LocalDateTime.now());
            movimiento.setTipoMovimiento(tipoMovimiento);
            movimiento.setValor(movimientoDto.getValor());
            movimiento.setSaldo(Boolean.TRUE.equals(isIncome) ? saldo.add(movimientoDto.getValor()) : saldo.subtract(movimientoDto.getValor()));
            movimiento.setEstado(Boolean.TRUE);
            movementRepository.save(movimiento);
            return movementRepository.findById(movimiento.getId(), IMovimientoResponse.class);
        });
    }


    /**
     * Method to consult a movement by id
     *
     * @param id Movement identifier
     * @param clase Class of the object to return
     * @return Object with the movement information
     */
    @Override
    public CompletableFuture<Object> searchMovementById(Long id, Class<?> clase) {
        return CompletableFuture.supplyAsync(() -> movementRepository.findById(id, clase));
    }


    /**
     * Method to consult movements by account
     *
     * @param accountId Account identifier
     * @return List with movement information
     */
    @Override
    public CompletableFuture<List<IMovimientoResponse>> searchMovementsByAccount(Long accountId) {
        return CompletableFuture.supplyAsync(() -> movementRepository.findByCuentaId(accountId, IMovimientoResponse.class));
    }


    /**
     * Method to consult movements by account and pagination
     *
     * @param accountId Account identifier
     * @param page Page number
     * @param recordsByPage Number of records per page
     * @param sortBy Field to sort by
     * @param address Address of the sort
     * @return Object with the information of the paged movements
     */
    @Override
    public CompletableFuture<Pagination<IMovimientoResponse>> searchMovementsByAccountAndPagination(Long accountId, Integer page, Integer recordsByPage, String sortBy, String address) {
        return CompletableFuture.supplyAsync(() -> {
            Pageable pageable = createObjectPagination(page, recordsByPage, sortBy, address);
            Long totalRecords = movementRepository.countByCuentaId(accountId);
            List<IMovimientoResponse> list = movementRepository.findByCuentaId(accountId, IMovimientoResponse.class, pageable);
            return new Pagination<>(list, page, recordsByPage, totalRecords.intValue());
        });
    }

    /**
     * Method to consult movements by account and date range
     *
     * @param accountId Account identifier
     * @param startDate Start date
     * @param endDate End date
     * @return List with movement information
     */
    @Override
    public CompletableFuture<Pagination<IMovimientoResponse>> searchMovementsByAccountAndBetweenDates(Long accountId, LocalDateTime startDate, LocalDateTime endDate, Integer page, Integer recordsByPage, String sortBy, String address) {
        return CompletableFuture.supplyAsync(() -> {
            Pageable pageable = createObjectPagination(page, recordsByPage, sortBy, address);
            Long totalRecords = movementRepository.countByCuentaIdAndFechaBetweenAndEstado(accountId, startDate, endDate, Boolean.TRUE);
            List<IMovimientoResponse> list = movementRepository.findByCuentaIdAndFechaBetweenAndEstado(accountId, startDate, endDate, Boolean.TRUE, IMovimientoResponse.class, pageable);
            return new Pagination<>(list, page, recordsByPage, totalRecords.intValue());
        });
    }


    /**
     * Method to create a pagination object
     *
     * @param page Page number
     * @param recordsPerPage Number of records per page
     * @param sortBy Field to sort by
     * @param address Address of the sort
     * @return Pagination object
     */
    private Pageable createObjectPagination(Integer page, Integer recordsPerPage, String sortBy, String address) {
        try {
            Sort sort = sortBy != null && address != null ? Sort.by(Sort.Direction.fromString(address), sortBy) : Sort.unsorted();
            return PageRequest.of(page - 1, recordsPerPage, sort);
        } catch (Exception e) {
            throw new BadRequestException("Error al consultar los movimientos, revise los parámetros de consulta");
        }
    }
}
