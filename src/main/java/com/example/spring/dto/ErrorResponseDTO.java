package com.example.spring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ErrorResponseDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy h:mm a") //HH:mm a 24 hour format - h:mm a 12 hour format
    private LocalDateTime timestamp;
    private Integer statusCode;
    private String descriptionCode;
    private String messageErrors;
    private String path;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(LocalDateTime timestamp, Integer statusCode, String descriptionCode, String messageErrors, String path) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.descriptionCode = descriptionCode;
        this.messageErrors = messageErrors;
        this.path = path;
    }

    // Getters and setters

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
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

    public String getMessageErrors() {
        return messageErrors;
    }

    public void setMessageErrors(String messageErrors) {
        this.messageErrors = messageErrors;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
