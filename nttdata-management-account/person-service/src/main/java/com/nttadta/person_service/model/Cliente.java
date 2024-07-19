package com.nttadta.person_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)

public class Cliente extends BaseEntity{
    @Column(name = "password", nullable = true, length = 200)
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_persona", nullable = false)
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "id_estado_cliente", nullable = false)
    private EstadoCliente estadoCliente;

    public void setPerson(Persona persona) {
        this.persona = persona.clone();
    }

    public Persona getPerson() {
        return persona.clone();
    }
}
