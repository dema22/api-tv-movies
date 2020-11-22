package com.example.spring.dto;

public class TvShowReminderPatchDTO {
    // Attributes
    private Boolean completed;
    private Integer currentSeason;
    private Integer currentEpisode;
    private Integer personalRating;

    // Constructor
    public TvShowReminderPatchDTO(Boolean completed, Integer currentSeason, Integer currentEpisode, Integer personalRating) {
        this.completed = completed;
        this.currentSeason = currentSeason;
        this.currentEpisode = currentEpisode;
        this.personalRating = personalRating;
    }

    public TvShowReminderPatchDTO() {
    }

    // Getters and Setters

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(Integer currentSeason) {
        this.currentSeason = currentSeason;
    }

    public Integer getCurrentEpisode() {
        return currentEpisode;
    }

    public void setCurrentEpisode(Integer currentEpisode) {
        this.currentEpisode = currentEpisode;
    }

    public Integer getPersonalRating() {
        return personalRating;
    }

    public void setPersonalRating(Integer personalRating) {
        this.personalRating = personalRating;
    }
}
