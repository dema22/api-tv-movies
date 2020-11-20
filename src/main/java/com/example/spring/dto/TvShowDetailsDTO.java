package com.example.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// It will ignore all unknown properties that we did not specified in the class.
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowDetailsDTO {

    // Attributes
    private String name;
    private Integer number_of_episodes;
    private Integer number_of_seasons;
    private String original_language;
    private String overview;
    private Double popularity;
    private String poster_path;
    private String status;

    // Constructor
    public TvShowDetailsDTO(String name, Integer number_of_episodes, Integer number_of_seasons, String original_language, String overview, Double popularity, String poster_path, String status) {
        this.name = name;
        this.number_of_episodes = number_of_episodes;
        this.number_of_seasons = number_of_seasons;
        this.original_language = original_language;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.status = status;
    }

    public TvShowDetailsDTO() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(Integer number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public Integer getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(Integer number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
