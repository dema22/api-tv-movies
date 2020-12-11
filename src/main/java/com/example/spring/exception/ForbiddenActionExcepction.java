package com.example.spring.exception;

public class ForbiddenActionExcepction extends Exception {
    public ForbiddenActionExcepction(String message) {
        super(message);
    }
}
