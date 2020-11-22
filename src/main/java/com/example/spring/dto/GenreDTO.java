package com.example.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// It will ignore all unknown properties that we did not specified in the class.
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenreDTO {

    private String name;

    // Constructor
    public GenreDTO(String name) {
        this.name = name;
    }

    public GenreDTO() {
    }

    // getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
