package com.example.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// It will ignore all unknown properties that we did not specified in the class.
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowDetailsDTO {

    // Attributes
    private String backdrop_path;
    private List<Integer> episode_run_time;
    private List<GenreDTO> genres;
    private String name;
    private Integer number_of_episodes;
    private Integer number_of_seasons;
    private String original_language;
    private String overview;
    private String poster_path;
    private String status;
    private String tagline;
    private String type;
    private VideosDTO videos;

    // Constructor

    public TvShowDetailsDTO(String backdrop_path, List<Integer> episode_run_time, List<GenreDTO> genres, String name, Integer number_of_episodes, Integer number_of_seasons, String original_language, String overview, String poster_path, String status, String tagline, String type, VideosDTO videos) {
        this.backdrop_path = backdrop_path;
        this.episode_run_time = episode_run_time;
        this.genres = genres;
        this.name = name;
        this.number_of_episodes = number_of_episodes;
        this.number_of_seasons = number_of_seasons;
        this.original_language = original_language;
        this.overview = overview;
        this.poster_path = poster_path;
        this.status = status;
        this.tagline = tagline;
        this.type = type;
        this.videos = videos;
    }

    public TvShowDetailsDTO() {
    }

    // Getters and Setters

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public List<Integer> getEpisode_run_time() {
        return episode_run_time;
    }

    public void setEpisode_run_time(List<Integer> episode_run_time) {
        this.episode_run_time = episode_run_time;
    }

    public List<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDTO> genres) {
        this.genres = genres;
    }

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

    public VideosDTO getVideos() {
        return videos;
    }

    public void setVideos(VideosDTO videos) {
        this.videos = videos;
    }
}
