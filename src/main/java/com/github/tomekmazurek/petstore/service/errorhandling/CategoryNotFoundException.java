package com.github.tomekmazurek.petstore.service.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Unable to find category with given id")
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException() {
        super();
    }

}
