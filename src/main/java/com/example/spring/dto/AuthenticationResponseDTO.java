package com.example.spring.dto;

public class AuthenticationResponseDTO {
    // Attributes
    private final String token;

    // Constructor

    public AuthenticationResponseDTO(String token) {
        this.token = token;
    }

    // Getters and setters

    public String getToken() {
        return token;
    }
}
