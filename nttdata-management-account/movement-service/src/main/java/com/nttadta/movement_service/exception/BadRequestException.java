package com.nttadta.movement_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for error of a bad request
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
    /**
     * Constructor
     * @param message Error Message
     */
    public BadRequestException(String message) {
        super(message);
    }
}
