package com.example.spring.dto;

import java.util.Optional;

public class TvShowReminderPatchDTO {
    // Attributes
    private Optional<Boolean> completed;
    private Optional<Integer> currentSeason;
    private Optional<Integer> currentEpisode;
    private Optional<Integer> personalRating;

    // Constructor
    public TvShowReminderPatchDTO(Optional<Boolean> completed, Optional<Integer> currentSeason, Optional<Integer> currentEpisode, Optional<Integer> personalRating) {
        this.completed = completed;
        this.currentSeason = currentSeason;
        this.currentEpisode = currentEpisode;
        this.personalRating = personalRating;
    }

    public TvShowReminderPatchDTO() {
    }

    // Getters and Setters

    public Optional<Boolean> getCompleted() {
        return completed;
    }

    public void setCompleted(Optional<Boolean> completed) {
        this.completed = completed;
    }

    public Optional<Integer> getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(Optional<Integer> currentSeason) {
        this.currentSeason = currentSeason;
    }

    public Optional<Integer> getCurrentEpisode() {
        return currentEpisode;
    }

    public void setCurrentEpisode(Optional<Integer> currentEpisode) {
        this.currentEpisode = currentEpisode;
    }

    public Optional<Integer> getPersonalRating() {
        return personalRating;
    }

    public void setPersonalRating(Optional<Integer> personalRating) {
        this.personalRating = personalRating;
    }
}
