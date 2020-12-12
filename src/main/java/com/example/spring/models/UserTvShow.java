package com.example.spring.models;

import javax.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "tv_show_created_by_user")
public class UserTvShow {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tv_show_created_by_user")
    private Integer idTvShowCreatedByUser;

    @ManyToOne()
    @JoinColumn(name= "id_user")
    @NotNull(message = "Provide user {idUser}")
    private User user;

    @Column(name = "name_tv_show")
    @NotNull(message = "Provide nameTvShow (String)")
    private String nameTvShow;

    private String genre;

    @Column(name = "production_company")
    private String productionCompany;

    // Getters and Setters

    public Integer getIdTvShowCreatedByUser() {
        return idTvShowCreatedByUser;
    }

    public void setIdTvShowCreatedByUser(Integer idTvShowCreatedByUser) {
        this.idTvShowCreatedByUser = idTvShowCreatedByUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNameTvShow() {
        return nameTvShow;
    }

    public void setNameTvShow(String nameTvShow) {
        this.nameTvShow = nameTvShow;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(String productionCompany) {
        this.productionCompany = productionCompany;
    }
}
