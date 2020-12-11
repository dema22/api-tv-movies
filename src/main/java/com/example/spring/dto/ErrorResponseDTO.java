package com.example.spring.dto;

import java.util.Date;

public class ErrorResponseDTO {

    private Date timestamp;
    private Integer statusCode;
    private String descriptionCode;
    private String message;
    private String path;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(Date timestamp, Integer statusCode, String descriptionCode, String message, String path) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.descriptionCode = descriptionCode;
        this.message = message;
        this.path = path;
    }

    // Getters and setters

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescriptionCode() {
        return descriptionCode;
    }

    public void setDescriptionCode(String descriptionCode) {
        this.descriptionCode = descriptionCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
