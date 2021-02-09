package com.example.spring.dto;

public class PageInfoDTO {
    private Integer pageIndex;
    private Integer size;

    public PageInfoDTO(Integer pageIndex, Integer size) {
        this.pageIndex = pageIndex;
        this.size = size;
    }

    public PageInfoDTO() {
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
