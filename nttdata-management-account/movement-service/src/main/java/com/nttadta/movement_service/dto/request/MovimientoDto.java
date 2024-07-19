package com.nttadta.movement_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class MovimientoDto implements Serializable {
    @NotNull(message = "El id de la cuenta no puede ser nulo")
    private Long idCuenta;

    private LocalDateTime fecha;

    @NotNull(message = "El id del tipo de movimiento no puede ser nulo")
    private Integer idTipoMovimiento;

    @Min(value = 0, message = "El valor debe ser mayor o igual a cero")
    private BigDecimal valor;

    private BigDecimal saldo;

    private Boolean estado;
}
