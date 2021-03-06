package com.github.tomekmazurek.petstore.service.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Product already exists in database")
public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException() {
        super();
    }
}
