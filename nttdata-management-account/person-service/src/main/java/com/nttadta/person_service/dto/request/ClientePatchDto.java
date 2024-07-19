package com.nttadta.person_service.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ClientePatchDto implements Serializable {
    private String password;

    private Integer idEstadoCliente;

    private String nombre;

    private String genero;

    private Integer edad;

    private String identificacion;

    private String direccion;

    private String telefono;
}
