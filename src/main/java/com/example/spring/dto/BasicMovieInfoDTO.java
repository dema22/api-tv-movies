package com.example.spring.dto;

public class BasicMovieInfoDTO {

    // Attributes
    private String adult;
    private Integer id;
    private String original_title;
    private Double popularity;
    private Boolean video;

    public BasicMovieInfoDTO(String adult, Integer id, String original_title, Double popularity, Boolean video) {
        this.adult = adult;
        this.id = id;
        this.original_title = original_title;
        this.popularity = popularity;
        this.video = video;
    }

    public BasicMovieInfoDTO() {
    }

    // G AND S

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }
}
