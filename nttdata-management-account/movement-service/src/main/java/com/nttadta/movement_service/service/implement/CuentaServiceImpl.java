package com.nttadta.movement_service.service.implement;

import com.nttadta.movement_service.constants.PathConstants;
import com.nttadta.movement_service.dto.request.CuentaDto;
import com.nttadta.movement_service.dto.request.CuentaPatchDto;
import com.nttadta.movement_service.dto.response.ICuentaResponse;
import com.nttadta.movement_service.exception.BadRequestException;
import com.nttadta.movement_service.exception.ResourceNotFoundException;
import com.nttadta.movement_service.model.Cliente;
import com.nttadta.movement_service.model.Cuenta;
import com.nttadta.movement_service.repository.CuentaRepository;
import com.nttadta.movement_service.service.CuentaService;
import com.nttadta.movement_service.service.EstadoCuentaService;
import com.nttadta.movement_service.service.TipoCuentaService;
import com.nttadta.movement_service.utils.Utils;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository accountRepository;
    private final TipoCuentaService accountTypeService;
    private final EstadoCuentaService accountStateService;
    private final RestTemplate restTemplate;

    /**
     * Method to create an account
     *
     * @param cuentaDto Object with the account information
     * @return Object with the information of the created account
     */
    @Override
    public CompletableFuture<Cuenta> create(CuentaDto cuentaDto) {
        return validateAndSaveAccount(null, cuentaDto);
    }

    /**
     * Method to update an account
     *
     * @param id Account identifier
     * @param cuentaDto Object with the account information
     * @return Object with updated account information
     */
    @Override
    public CompletableFuture<Cuenta> update(Long id, CuentaDto cuentaDto) {
        return validateAndSaveAccount(id, cuentaDto);
    }

    /**
     * Method to update an account by fields
     *
     * @param id Account identifier
     * @param cuentaDto Object with the account information
     * @return Object with updated account information
     */
    @Override
    public CompletableFuture<ICuentaResponse> updateByFields(Long id, CuentaPatchDto cuentaDto) {
        return CompletableFuture.supplyAsync(() -> {
            Cuenta cuenta = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PathConstants.ACCOUNT, "id", id));

            if (cuentaDto.getIdCliente() != null)
                cuenta.setCliente(searchClientById(cuentaDto.getIdCliente()));
            if (cuentaDto.getNumeroCuenta() != null)
                cuenta.setNumeroCuenta(cuentaDto.getNumeroCuenta());
            if (cuentaDto.getIdTipoMovimiento() != null)
                cuenta.setTipoCuenta(accountTypeService.searchAccountTypeById(cuentaDto.getIdTipoMovimiento()).join());
            if (cuentaDto.getSaldoInicial() != null)
                cuenta.setSaldoInicial(cuentaDto.getSaldoInicial());
            if (cuentaDto.getIdEstadoCuenta() != null)
                cuenta.setEstadoCuenta(accountStateService.searchAccountStateById(cuentaDto.getIdEstadoCuenta()).join());

            accountRepository.save(cuenta);
            return accountRepository.findById(cuenta.getId(), ICuentaResponse.class);
        });
    }


    /**
     * Method to check an account by id
     *
     * @param id Account identifier
     * @param clase Entity class
     * @return Object with the account information
     */
    @Override
    public CompletableFuture<Object> searchAccountById(Long id, Class<?> clase) {
        return CompletableFuture.supplyAsync(() -> {
            Object cuenta = accountRepository.findById(id, clase);
            if (cuenta == null)
                throw new ResourceNotFoundException(PathConstants.ACCOUNT, "id", id);
            return cuenta;
        });
    }


    /**
     * Method to check all accounts
     *
     * @return List with account information
     */
    @Override
    public CompletableFuture<List<ICuentaResponse>> searchAccounts() {
        return CompletableFuture.supplyAsync(() -> accountRepository.findAll(ICuentaResponse.class));
    }


    /**
     * Method to consult all accounts of a client
     *
     * @param id Client identifier
     * @return List with account information
     */
    private CompletableFuture<Cuenta> validateAndSaveAccount(Long id, CuentaDto cuentaDto) {
        return CompletableFuture.supplyAsync(() -> {
            Cuenta cuenta = id != null ? accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PathConstants.ACCOUNT, "id", id)) : new Cuenta();
            if (cuenta.getCliente() != null && cuenta.getCliente().getId() != cuentaDto.getIdCliente() && accountRepository.existsByCliente_Id(cuentaDto.getIdCliente()))
                throw new BadRequestException("El cliente ya tiene una cuenta asociada");

            if (cuenta.getCliente() == null && accountRepository.existsByCliente_Id(cuentaDto.getIdCliente()))
                throw new BadRequestException("El cliente ya tiene una cuenta asociada");

            Cliente cliente = searchClientById(cuentaDto.getIdCliente());
            if (cliente == null)
                throw new ResourceNotFoundException(PathConstants.CLIENT, "id", cuentaDto.getIdCliente());

            cuenta.setCliente(cliente);
            cuenta.setNumeroCuenta(cuentaDto.getNumeroCuenta());
            cuenta.setTipoCuenta(accountTypeService.searchAccountTypeById(cuentaDto.getIdTipoCuenta()).join());
            cuenta.setSaldoInicial(cuentaDto.getSaldoInicial());
            cuenta.setEstadoCuenta(accountStateService.searchAccountStateById(cuentaDto.getIdEstadoCuenta()).join());
            return accountRepository.save(cuenta);
        });
    }


    /**
     * Method with circuit breaker to query a client by id
     * @param id Client identifier
     * @return Object with the customer information
     */
    @CircuitBreaker(name = "persona-service", fallbackMethod = "fallbackConsultarClientePorId")
    private Cliente searchClientById(Long id) {
        try {
            String url = Utils.dockerIsRunning() ? PathConstants.DOCKER_HOST_PERSON : PathConstants.LOCAL_HOST_PERSON;
            url = url + PathConstants.CLIENT + "/" + id;
            return restTemplate.getForObject(url, Cliente.class);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResourceNotFoundException(PathConstants.CLIENT, "id", id);
            } else {
                throw ex;
            }
        }
    }

    /**
     * Method to handle the fallback of querying a client by id
     * @param id Client identifier
     * @param ex Exception thrown
     * @return Object with the customer information
     */
    public Cliente fallbackSearchClientById(Long id, Exception ex) throws ResourceNotFoundException {
        throw new BadRequestException("Servicio de consulta de cliente no disponible");
    }
}
