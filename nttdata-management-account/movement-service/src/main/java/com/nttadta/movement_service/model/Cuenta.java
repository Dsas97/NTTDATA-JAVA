package com.nttadta.movement_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "cuenta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Cuenta extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "numero_cuenta", length = 100)
    private String numeroCuenta;

    @ManyToOne
    @JoinColumn(name = "id_tipo_cuenta", nullable = false)
    private TipoCuenta tipoCuenta;

    @Column(name = "saldo_inicial", precision = 17, scale = 2)
    private BigDecimal saldoInicial;

    @ManyToOne
    @JoinColumn(name = "id_estado_cuenta", nullable = false)
    private EstadoCuenta estadoCuenta;
}
