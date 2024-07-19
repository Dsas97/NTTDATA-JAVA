package com.nttadta.movement_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "persona")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Persona extends BaseEntity{


    @Column(name = "nombre")
    private String nombre;

    @Column(name = "genero", length = 100)
    private String genero;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "identificacion", length = 20)
    private String identificacion;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono", length = 10)
    private String telefono;

}
