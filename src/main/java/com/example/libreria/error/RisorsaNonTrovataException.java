package com.example.libreria.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RisorsaNonTrovataException extends RuntimeException {

    public RisorsaNonTrovataException(String messaggio) {
        super(messaggio);
    }
}
