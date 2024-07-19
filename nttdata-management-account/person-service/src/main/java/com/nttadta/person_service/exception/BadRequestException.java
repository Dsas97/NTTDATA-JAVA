package com.nttadta.person_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for bad request errors
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
    /**
     * Constructor
     * @param message Error message
     */
    public BadRequestException(String message) {
        super(message);
    }
}
