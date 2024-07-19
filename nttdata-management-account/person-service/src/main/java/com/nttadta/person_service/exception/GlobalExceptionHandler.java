package com.nttadta.person_service.exception;

import com.nttadta.person_service.exception.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to manage global excpetions
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Method to handle the exceptions of field validations
     * @param ex Exception
     * @param webRequest Request
     * @return Error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String key = ((FieldError)error).getField();
            String value = error.getDefaultMessage();
            errors.put(key, value);
        });
        ApiResponse apiResponse = new ApiResponse(errors.toString(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to handle the exceptions of resources not found
     * @param ex Exception
     * @param webRequest Request
     * @return Error response
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Method to handle the exceptions of bad request
     * @param ex Exception
     * @param webRequest Request
     * @return Error response
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handlerBadRequestException(BadRequestException ex, WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to handle the exceptions of error server internal
     * @param ex Excepción
     * @param webRequest Petición
     * @return Respuesta de error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handlerException(BadRequestException ex, WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
