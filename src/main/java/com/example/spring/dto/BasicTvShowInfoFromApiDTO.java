package com.example.spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BasicTvShowInfoFromApiDTO {

    private Integer id;
    @JsonProperty("original_name")
    private String originalName;
    private Float popularity;

    public BasicTvShowInfoFromApiDTO(Integer id, String originalName, Float popularity) {
        this.id = id;
        this.originalName = originalName;
        this.popularity = popularity;
    }

    public BasicTvShowInfoFromApiDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Float getPopularity() {
        return popularity;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }
}
