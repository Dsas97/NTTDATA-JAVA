package com.nttadta.person_service.dto.response;

import org.springframework.beans.factory.annotation.Value;

public interface IClienteResponse {
    Long getId();

    String getPassword();

    @Value("#{target.persona != null ? target.persona.nombre : ''}")
    String getNombre();

    @Value("#{target.persona != null ? target.persona.genero : ''}")
    String getGenero();

    @Value("#{target.persona != null ? target.persona.edad : 0}")
    Integer getEdad();

    @Value("#{target.persona != null ? target.persona.identificacion : ''}")
    String getIdentificacion();

    @Value("#{target.persona != null ? target.persona.direccion : ''}")
    String getDireccion();

    @Value("#{target.persona != null ? target.persona.telefono : ''}")
    String getTelefono();

    @Value("#{target.estadoCliente != null ? target.estadoCliente.nombre  : ''}")
    String getNombreEstadoCliente();
}
