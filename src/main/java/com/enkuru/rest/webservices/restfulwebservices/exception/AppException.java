package com.enkuru.rest.webservices.restfulwebservices.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class AppException extends RuntimeException {

    private Date timeStamp;

    private String message;

    private String details;

    public AppException(String message) {
        super(message);
        this.message = message;
    }
}

