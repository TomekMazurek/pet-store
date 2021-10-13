package com.github.tomekmazurek.petstore.service.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Argument")
public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException() {
        super();
    }
}
