package com.example.spring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "basic_tv_show_info")

public class BasicTvShowInfo {

    @Id
    @Column(name = "id_basic_tv_show_info")
    @NotNull
    private Integer id;

    @Column(name = "original_name")
    @JsonProperty("original_name")
    @NotNull
    private String originalName;

    // getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }
}
