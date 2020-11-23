package com.example.spring.dto;

import org.openapitools.jackson.nullable.JsonNullable;

public class TvShowReminderPatchDTO {
    // Attributes
    private JsonNullable<Boolean> completed = JsonNullable.undefined();
    private JsonNullable<Integer> currentSeason = JsonNullable.undefined();
    private JsonNullable<Integer> currentEpisode = JsonNullable.undefined();
    private JsonNullable<Integer> personalRating = JsonNullable.undefined();

    // Constructor
    public TvShowReminderPatchDTO() {
    }

    // Getters and Setters
    public JsonNullable<Boolean> getCompleted() {
        return completed;
    }

    public JsonNullable<Integer> getCurrentSeason() {
        return currentSeason;
    }

    public JsonNullable<Integer> getCurrentEpisode() {
        return currentEpisode;
    }

    public JsonNullable<Integer> getPersonalRating() {
        return personalRating;
    }
}
