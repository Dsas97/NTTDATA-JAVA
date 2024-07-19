package com.nttadta.movement_service.exception.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Class to handle the response of a request
 */
@Data
@NoArgsConstructor
public class ApiResponse {
    private Date time = new Date();
    private String message;
    private String url;

    /**
     * Constructor
     * @param message Message
     * @param url URL
     */
    public ApiResponse(String message, String url) {
        this.message = message;
        this.url = url.replace("uri=","");
    }
}
