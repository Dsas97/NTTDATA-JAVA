package com.nttadta.movement_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Movimiento extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "id_cuenta", nullable = false)
    private Cuenta cuenta;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "id_tipo_movimiento", nullable = false)
    private TipoMovimiento tipoMovimiento;

    @Column(name = "valor", precision = 17, scale = 2)
    private BigDecimal valor;

    @Column(name = "saldo", precision = 17, scale = 2)
    private BigDecimal saldo;

    @Column(name = "estado")
    private Boolean estado;
}
