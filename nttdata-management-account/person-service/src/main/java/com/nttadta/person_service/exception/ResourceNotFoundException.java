package com.nttadta.person_service.exception;



import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Error for excpetion of resources not found
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    /**
     * Constructor
     * @param resourceName Resource name
     * @param fieldName Field Name
     * @param fieldValue Field Value
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue){
        super(String.format("%s no se encontró con %s = %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    /**
     * Constructor
     * @param resourceName Resource Name
     */
    public ResourceNotFoundException(String resourceName){
        super(String.format("%s no se encontró con %s = %s", resourceName));
        this.resourceName = resourceName;
    }
}
