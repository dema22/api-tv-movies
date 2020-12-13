package com.example.spring.dto;

public class TvShowReminderResponseDTO {
    // Attributes
    private Integer idTvShowReminder;
    private UserTvShowDTO userTvShowDTO;
    private TvShowDetailsResponseDTO tvShowDetailsResponseDTO;
    private Boolean completed;
    private Integer currentSeason;
    private Integer currentEpisode;
    private Integer personalRating;

    // Constructor
    public TvShowReminderResponseDTO(Integer idTvShowReminder, UserTvShowDTO userTvShowDTO, TvShowDetailsResponseDTO tvShowDetailsResponseDTO, Boolean completed, Integer currentSeason, Integer currentEpisode, Integer personalRating) {
        this.idTvShowReminder = idTvShowReminder;
        this.userTvShowDTO = userTvShowDTO;
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

    public UserTvShowDTO getUserTvShowDTO() {
        return userTvShowDTO;
    }

    public void setUserTvShowDTO(UserTvShowDTO userTvShowDTO) {
        this.userTvShowDTO = userTvShowDTO;
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
