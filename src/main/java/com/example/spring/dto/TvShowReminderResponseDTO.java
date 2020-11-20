package com.example.spring.dto;

import com.example.spring.models.User;
import com.example.spring.models.UserTvShow;

public class TvShowReminderResponseDTO {
    // Attributes
    private User user;
    private UserTvShow userTvShow;
    private TvShowDetailsDTO tvShowDetailsDTO;
    private Boolean completed;
    private Integer currentSeason;
    private Integer currentEpisode;
    private Integer personalRating;

    public TvShowReminderResponseDTO(User user, UserTvShow userTvShow, TvShowDetailsDTO tvShowDetailsDTO, Boolean completed, Integer currentSeason, Integer currentEpisode, Integer personalRating) {
        this.user = user;
        this.userTvShow = userTvShow;
        this.tvShowDetailsDTO = tvShowDetailsDTO;
        this.completed = completed;
        this.currentSeason = currentSeason;
        this.currentEpisode = currentEpisode;
        this.personalRating = personalRating;
    }

    public TvShowReminderResponseDTO() {
    }

    // Getters and setters

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserTvShow getUserTvShow() {
        return userTvShow;
    }

    public void setUserTvShow(UserTvShow userTvShow) {
        this.userTvShow = userTvShow;
    }

    public TvShowDetailsDTO getTvShowDetailsDTO() {
        return tvShowDetailsDTO;
    }

    public void setTvShowDetailsDTO(TvShowDetailsDTO tvShowDetailsDTO) {
        this.tvShowDetailsDTO = tvShowDetailsDTO;
    }

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
