package com.nttadta.movement_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for error of resources not found
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private String resourceName;
    private String fieldName;
    private Object fieldValue;


    /**
     * Constructor for resource not found
     * @param resourceName Resource name
     * @param fieldName Field name
     * @param fieldValue Field value
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue){
        super(String.format("%s no se encontró con %s = %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }


    /**
     * Constructor for resource not found
     * @param resourceName Resource name
     */
    public ResourceNotFoundException(String resourceName){
        super(String.format("No hay registros de %s en el sistema", resourceName));
        this.resourceName = resourceName;
    }
}
