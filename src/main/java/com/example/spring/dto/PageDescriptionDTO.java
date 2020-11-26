package com.example.spring.dto;

public class PageDescriptionDTO {
    // Attributes
    private Integer page;
    private Integer size;
    private Integer total;

    // Constructor

    public PageDescriptionDTO(Integer page, Integer size, Integer total) {
        this.page = page;
        this.size = size;
        this.total = total;
    }

    public PageDescriptionDTO() {
    }

    // Getters and Setters

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
