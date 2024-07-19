package com.nttadta.movement_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estado_cuenta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = true, length = 100)
    private String nombre;
}
