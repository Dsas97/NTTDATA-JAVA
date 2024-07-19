package com.nttadta.movement_service.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CuentaPatchDto {
    private Long idCliente;

    private String numeroCuenta;

    private Integer idTipoMovimiento;

    private BigDecimal saldoInicial;

    private Integer idEstadoCuenta;
}
