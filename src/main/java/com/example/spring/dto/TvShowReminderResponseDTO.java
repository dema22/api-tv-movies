package com.example.spring.dto;

import com.example.spring.models.User;
import com.example.spring.models.UserTvShow;

public class TvShowReminderResponseDTO {
    // Attributes
    private Integer idTvShowReminder;
    private User user;
    private UserTvShow userTvShow;
    private TvShowDetailsResponseDTO tvShowDetailsResponseDTO;
    private Boolean completed;
    private Integer currentSeason;
    private Integer currentEpisode;
    private Integer personalRating;

    // Constructor
    public TvShowReminderResponseDTO(Integer idTvShowReminder, User user, UserTvShow userTvShow, TvShowDetailsResponseDTO tvShowDetailsResponseDTO, Boolean completed, Integer currentSeason, Integer currentEpisode, Integer personalRating) {
        this.idTvShowReminder = idTvShowReminder;
        this.user = user;
        this.userTvShow = userTvShow;
        this.tvShowDetailsResponseDTO = tvShowDetailsResponseDTO;
        this.completed = completed;
        this.currentSeason = currentSeason;
        this.currentEpisode = currentEpisode;
        this.personalRating = personalRating;
    }

    public TvShowReminderResponseDTO() {
    }

    // Getters and setters
    public Integer getIdTvShowReminder() {
        return idTvShowReminder;
    }

    public void setIdTvShowReminder(Integer idTvShowReminder) {
        this.idTvShowReminder = idTvShowReminder;
    }

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

    public TvShowDetailsResponseDTO getTvShowDetailsResponseDTO() {
        return tvShowDetailsResponseDTO;
    }

    public void setTvShowDetailsResponseDTO(TvShowDetailsResponseDTO tvShowDetailsResponseDTO) {
        this.tvShowDetailsResponseDTO = tvShowDetailsResponseDTO;
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
