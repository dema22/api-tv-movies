package com.example.spring.models;

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
    @NotNull
    private String original_name;

    // getters and setters
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
}
