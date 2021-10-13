package com.github.tomekmazurek.petstore.service.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Unable to find product")
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super();
    }

}
