package com.example.spring.models;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "tv_show_created_by_user")
public class UserTvShow {

    // Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tv_show_created_by_user")
    @NotNull
    private Integer idTvShowCreatedByUser;

    @Column(name = "name_tv_show")
    @NotNull
    private String nameTvShow;

    @NotNull
    private String genre;

    @Column(name = "production_company")
    @NotNull
    private String productionCompany;

    // Getters and Setters

    public Integer getIdTvShowCreatedByUser() {
        return idTvShowCreatedByUser;
    }

    public void setIdTvShowCreatedByUser(Integer idTvShowCreatedByUser) {
        this.idTvShowCreatedByUser = idTvShowCreatedByUser;
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
