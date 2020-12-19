package com.example.spring.dto;

import java.util.List;

public class PageResponseDTO<T> {
    // Attributes
    private List<T> items;
    private PageDescriptionDTO pageDescriptionDTO;

    // Constructor

    public PageResponseDTO(List<T> items, PageDescriptionDTO pageDescriptionDTO) {
        this.items = items;
        this.pageDescriptionDTO = pageDescriptionDTO;
    }

    public PageResponseDTO() {
    }

    // Getters and Setters

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public PageDescriptionDTO getPageDescriptionDTO() {
        return pageDescriptionDTO;
    }

    public void setPageDescriptionDTO(PageDescriptionDTO pageDescriptionDTO) {
        this.pageDescriptionDTO = pageDescriptionDTO;
    }

}
