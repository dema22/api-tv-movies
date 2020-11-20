package com.example.spring.models;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "tv_show_reminder")
public class TvShowReminder {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_tv_show_reminder")
    @NotNull
    private Integer idTvShowReminder;

    // By default is eager,so we will always get the nested tv show on the response.
    // Optional false, means the relation is mandatory we need a tv show, we can not save a tv show reminder with out assign it a tv show.

    @ManyToOne(optional = false)
    @JoinColumn(name="id_user")
    @NotNull
    private User user;

    @ManyToOne()
    @JoinColumn(name= "id_basic_tv_show_info")
    private BasicTvShowInfo basicTvShowInfo;

    @ManyToOne()
    @JoinColumn(name= "id_tv_show_created_by_user")
    private UserTvShow userTvShow;

    private Boolean completed;

    @Column(name = "current_season")
    private Integer currentSeason;

    @Column(name = "current_episode")
    private Integer currentEpisode;

    @Column(name = "personal_rating")
    private Integer personalRating;

    // GETTERS AND SETTERS

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

    public BasicTvShowInfo getBasicTvShowInfo() {
        return basicTvShowInfo;
    }

    public void setBasicTvShowInfo(BasicTvShowInfo basicTvShowInfo) {
        this.basicTvShowInfo = basicTvShowInfo;
    }

    public UserTvShow getUserTvShow() {
        return userTvShow;
    }

    public void setUserTvShow(UserTvShow userTvShow) {
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
}
