package com.example.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// It will ignore all unknown properties that we did not specified in the class.
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoResultsDTO {

    // Attributes
    private String site;
    private String key;
    private String type;

    // Constructors
    public VideoResultsDTO(String site, String key, String type) {
        this.site = site;
        this.key = key;
        this.type = type;
    }

    public VideoResultsDTO() {
    }

    // getters and setters

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
