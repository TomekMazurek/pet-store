package com.github.tomekmazurek.petstore.service.errorhandling;

public class CategoryExistException extends RuntimeException {

    public CategoryExistException() {
    }

    public CategoryExistException(String message) {
        super(message);
    }

    public CategoryExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryExistException(Throwable cause) {
        super(cause);
    }
}
