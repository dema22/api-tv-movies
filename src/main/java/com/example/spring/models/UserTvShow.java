package com.example.spring.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class UserTvShow {

    // Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTvShow;

    private String name;
    private Integer numberEpisodes;
    private Date releaseDate;
    private Date finishDate;
    private String productionCompany;
    private String genre;

    public UserTvShow() {
    }

    // Getters and Setters

    public Integer getIdTvShow() {
        return idTvShow;
    }

    public void setIdTvShow(Integer idTvShow) {
        this.idTvShow = idTvShow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberEpisodes() {
        return numberEpisodes;
    }

    public void setNumberEpisodes(Integer numberEpisodes) {
        this.numberEpisodes = numberEpisodes;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(String productionCompany) {
        this.productionCompany = productionCompany;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }








    // By default is LAZY, the related tv show reminders wont we loaded with the request tv show.
    // We will have to explicity ask for them.

    //@OneToMany(mappedBy = "tvShow")
    //@JsonIgnore
    //private List<TvShowReminder> tvShowReminders;

}
