package com.example.spring.exception;

public class InvalidLoginException extends Exception{
    public InvalidLoginException(String message) {
        super(message);
    }
}
