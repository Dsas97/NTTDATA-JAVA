package com.nttadta.movement_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CuentaDto implements Serializable {

    @NotNull(message = "El campo id cliente es requerido")
    private Long idCliente;

    @NotBlank(message = "El campo numero de cuenta es requerido")
    private String numeroCuenta;

    @NotNull(message = "El campo id tipo cuenta es requerido")
    private Integer idTipoCuenta;

    @Min(value = 0, message = "El saldo inicial debe ser mayor o igual a cero")
    private BigDecimal saldoInicial;

    @NotNull(message = "El campo id estado cuenta es requerido")
    private Integer idEstadoCuenta;
}
