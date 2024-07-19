package com.nttadta.person_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Getter
@FieldNameConstants
@MappedSuperclass
public class BaseEntity implements Serializable {
    public static final String PROPERTY_ID = "id";
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public void setId(Long id) {
        this.id = id;
    }
}
