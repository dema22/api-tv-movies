package com.example.spring.exception;

public class BusinessLogicValidationFailure extends Exception {
    public BusinessLogicValidationFailure(String message) {
        super(message);
    }
}
