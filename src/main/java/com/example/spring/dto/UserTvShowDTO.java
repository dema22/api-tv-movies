package com.example.spring.dto;

public class UserTvShowDTO {
    // Attributes
    private Integer idTvShowCreatedByUser;
    private String nameTvShow;
    private String genre;
    private String productionCompany;

    // Constructor

    public UserTvShowDTO(Integer idTvShowCreatedByUser, String nameTvShow, String genre, String productionCompany) {
        this.idTvShowCreatedByUser = idTvShowCreatedByUser;
        this.nameTvShow = nameTvShow;
        this.genre = genre;
        this.productionCompany = productionCompany;
    }

    public UserTvShowDTO() {
    }

    // Getters and setters

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
