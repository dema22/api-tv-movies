package com.example.spring.dto;

public class PageDescriptionDTO {
    // Attributes
    private Integer totalPages;
    private Long totalElements;
    private Integer numberOfElementsReturn;

    // Constructor


    public PageDescriptionDTO(Integer totalPages, Long totalElements, Integer numberOfElementsReturn) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.numberOfElementsReturn = numberOfElementsReturn;
    }

    public PageDescriptionDTO() {
    }

    // Getters and Setters

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getNumberOfElementsReturn() {
        return numberOfElementsReturn;
    }

    public void setNumberOfElementsReturn(Integer numberOfElementsReturn) {
        this.numberOfElementsReturn = numberOfElementsReturn;
    }
}
