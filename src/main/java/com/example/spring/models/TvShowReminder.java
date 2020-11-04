package com.example.spring.models;

import javax.persistence.*;

@Entity
public class TvShowReminder {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTvShowReminder;

    // By default is eager,so we will always get the nested tv show on the response.
    // Optional false, means the relation is mandatory we need a tv show, we can not save a tv show reminder with out assign it a tv show.

    @ManyToOne(optional = false)
    @JoinColumn(name= "ID_TVSHOW")
    private UserTvShow userTvShow;

    @ManyToOne(optional = false)
    @JoinColumn(name="ID_USER")
    private User user;

    private Boolean completed;
    private Integer currentSeason;
    private Integer currentEpisode;
    private Integer personalRating;



    // GETTERS AND SETTERS

    public Integer getIdTvShowReminder() {
        return idTvShowReminder;
    }

    public void setIdTvShowReminder(Integer idTvShowReminder) {
        this.idTvShowReminder = idTvShowReminder;
    }

    public UserTvShow getTvShow() {
        return userTvShow;
    }

    public void setTvShow(UserTvShow userTvShow) {
        this.userTvShow = userTvShow;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
