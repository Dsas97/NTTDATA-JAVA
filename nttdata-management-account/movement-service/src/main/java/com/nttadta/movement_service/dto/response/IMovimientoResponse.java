package com.nttadta.movement_service.dto.response;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IMovimientoResponse {
    @Value("#{target.cuenta != null ? target.cuenta.numeroCuenta : ''}")
    String getNumeroCuenta();

    @Value("#{target.cuenta.cliente != null ? target.cuenta.cliente.persona.nombre : ''}")
    String getNombreCliente();

    @Value("#{target.cuenta.tipoCuenta != null ? target.cuenta.tipoCuenta.nombre : ''}")
    String getNombreTipoCuenta();

    @Value("#{target.cuenta != null ? target.cuenta.saldoInicial : 0}")
    BigDecimal getSaldoInicial();

    @Value("#{target.cuenta != null ? target.cuenta.id : 0}")
    Long getIdCuenta();

    LocalDateTime getFecha();

    @Value("#{target.tipoMovimiento != null ? target.tipoMovimiento.id : 0}")
    Integer getIdTipoMovimiento();

    @Value("#{target.tipoMovimiento != null ? target.tipoMovimiento.nombre : ''}")
    String getNombreTipoMovimiento();

    BigDecimal getValor();

    BigDecimal getSaldo();
}
