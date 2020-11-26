package com.example.spring.dto;

import java.util.List;

public class PageTvShowRemindersResponseDTO<T> {
    // Attributes
    private List<T> tvShowRemindersResponseDTO;
    private PageDescriptionDTO pageDescriptionDTO;

    // Constructor
    public PageTvShowRemindersResponseDTO(List<T> tvShowRemindersResponseDTO, PageDescriptionDTO pageDescriptionDTO) {
        this.tvShowRemindersResponseDTO = tvShowRemindersResponseDTO;
        this.pageDescriptionDTO = pageDescriptionDTO;
    }

    public PageTvShowRemindersResponseDTO() {
    }

    // Getters and Setters
    public List<T> getTvShowRemindersResponseDTO() {
        return tvShowRemindersResponseDTO;
    }

    public void setTvShowRemindersResponseDTO(List<T> tvShowRemindersResponseDTO) {
        this.tvShowRemindersResponseDTO = tvShowRemindersResponseDTO;
    }

    public PageDescriptionDTO getPageDescriptionDTO() {
        return pageDescriptionDTO;
    }

    public void setPageDescriptionDTO(PageDescriptionDTO pageDescriptionDTO) {
        this.pageDescriptionDTO = pageDescriptionDTO;
    }
}
