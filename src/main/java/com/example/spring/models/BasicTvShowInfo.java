package com.example.spring.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BasicTvShowInfo {

    @Id
    @Column(name = "id_basic_tv_show_info")
    private Integer id;

    @Column(name = "original_name")
    private String original_name;

    /*public BasicTvShowInfo(Integer idBasicTvShowInfo, String originalName) {
        this.idBasicTvShowInfo = idBasicTvShowInfo;
        this.originalName = originalName;
    }*/

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
