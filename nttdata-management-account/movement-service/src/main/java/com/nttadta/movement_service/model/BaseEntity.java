package com.nttadta.movement_service.model;

import jakarta.persistence.*;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@FieldNameConstants
@MappedSuperclass
public class BaseEntity implements Serializable {
    public static final String ID_PROPERTY = "id";
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
