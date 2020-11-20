package com.example.spring.dto;

import java.util.List;

public class VideosDTO {
    // Attributes
    private List<VideoResultsDTO> results;

    // Constructor

    public VideosDTO(List<VideoResultsDTO> results) {
        this.results = results;
    }

    public VideosDTO() {
    }

    // Getters and Setters

    public List<VideoResultsDTO> getResults() {
        return results;
    }

    public void setResults(List<VideoResultsDTO> results) {
        this.results = results;
    }
}
