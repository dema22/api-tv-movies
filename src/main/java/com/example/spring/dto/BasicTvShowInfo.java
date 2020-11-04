package com.example.spring.dto;

public class BasicTvShowInfo {
    private Integer id;
    private String original_name;
    private Double popularity;

    public BasicTvShowInfo(Integer id, String original_name, Double popularity) {
        this.id = id;
        this.original_name = original_name;
        this.popularity = popularity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }
}
