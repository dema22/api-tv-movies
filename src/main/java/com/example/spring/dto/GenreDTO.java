package com.example.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GenreDTO {
    @JsonIgnore
    private Integer id;
    private String name;

    // contructor
    public GenreDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreDTO() {
    }

    // getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
