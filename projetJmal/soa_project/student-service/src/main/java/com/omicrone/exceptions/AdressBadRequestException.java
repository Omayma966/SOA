package com.omicrone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AdressBadRequestException extends RuntimeException{

    public AdressBadRequestException(String message) {
        super(message);
    }
}
