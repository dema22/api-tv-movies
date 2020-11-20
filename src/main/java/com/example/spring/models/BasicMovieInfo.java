package com.example.spring.models;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "basic_movie_info")
public class BasicMovieInfo {

    // Attributes
    @Id
    @Column(name = "id_basic_movie_info")
    @NotNull
    private Integer id;

    @NotNull
    private String original_title;

    // Getters and Setters
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
}
