package com.nttadta.movement_service.dto.response;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface ICuentaResponse {
    @Value("#{target.cliente != null ? target.cliente.id : 0}")
    Long getIdCliente();

    @Value("#{target.cliente != null ? target.cliente.persona.nombre : ''}")
    String getNombreCliente();

    String getNumeroCuenta();

    @Value("#{target.tipoCuenta != null ? target.tipoCuenta.id : 0}")
    Integer getIdTipoCuenta();

    @Value("#{target.tipoCuenta != null ? target.tipoCuenta.nombre : ''}")
    String getNombreTipoCuenta();

    BigDecimal getSaldoInicial();

    @Value("#{target.estadoCuenta != null ? target.estadoCuenta.id : 0}")
    Integer getIdEstadoCuenta();

    @Value("#{target.estadoCuenta != null ? target.estadoCuenta.nombre : ''}")
    String getNombreEstadoCuenta();
}
