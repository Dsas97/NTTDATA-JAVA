package com.nttadta.person_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDto implements Serializable {

    @NotEmpty(message = "El nombre es requerido")
    private String nombre;

    @NotEmpty(message = "El género es requerido")
    private String genero;

    @NotNull(message = "La edad es requerida")
    private Integer edad;

    @Size(min = 10, max = 20, message = "La identificación debe tener entre 10 y 20 caracteres")
    private String identifiacion;

    @Size(min = 10, max = 255, message = "La dirección debe tener entre 10 y 255 caracteres")
    private String direccion;

    @Size(min = 7, max = 10, message = "El teléfono debe tener entre 8 y 10 caracteres")
    private String telefono;
}
