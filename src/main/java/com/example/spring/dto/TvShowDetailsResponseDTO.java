package com.example.spring.dto;


import java.util.List;

public class TvShowDetailsResponseDTO {

    // Attributes
    private String backdropPath;
    private List<Integer> episodeRunTime;
    private List<String> genres;
    private String name;
    private Integer numberOfEpisodes;
    private Integer numberOfSeasons;
    private String originalLanguage;
    private String overview;
    private String posterPath;
    private String status;
    private String tagline;
    private String type;
    private List<String> trailersURL;

    // Constructor

    public TvShowDetailsResponseDTO(String backdropPath, List<Integer> episodeRunTime, List<String> genres, String name, Integer numberOfEpisodes, Integer numberOfSeasons, String originalLanguage, String overview, String posterPath, String status, String tagline, String type, List<String> trailersURL) {
        this.backdropPath = backdropPath;
        this.episodeRunTime = episodeRunTime;
        this.genres = genres;
        this.name = name;
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfSeasons = numberOfSeasons;
        this.originalLanguage = originalLanguage;
        this.overview = overview;
        this.posterPath = posterPath;
        this.status = status;
        this.tagline = tagline;
        this.type = type;
        this.trailersURL = trailersURL;
    }

    public TvShowDetailsResponseDTO() {
    }

    // Getters and setters


    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public List<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public void setEpisodeRunTime(List<Integer> episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(Integer numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public Integer getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(Integer numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTrailersURL() {
        return trailersURL;
    }

    public void setTrailersURL(List<String> trailersURL) {
        this.trailersURL = trailersURL;
    }
}
