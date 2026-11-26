package com.demo.health_dekho_app.exception;

public class CustomerAlreadyExistsException
        extends RuntimeException {

    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
