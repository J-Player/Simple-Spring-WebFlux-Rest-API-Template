package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CookieNotFoundException extends RuntimeException {

    public CookieNotFoundException(String message) {
        super(message);
    }

}
