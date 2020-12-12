package com.example.spring.dto;

import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.constraints.NotNull;

public class UserTvShowPatchDTO {
    // Attributes

    /// This will make the application return a 400 error if the value of name is not omitted and explicitly set to null.
    @NotNull(message = "Provide nameTvShow (String)")
    private JsonNullable<String> nameTvShow = JsonNullable.undefined();
    private JsonNullable<String> genre = JsonNullable.undefined();
    private JsonNullable<String> productionCompany = JsonNullable.undefined();

    // Constructor
    public UserTvShowPatchDTO() {
    }

    // Getters
    public JsonNullable<String> getNameTvShow() {
        return nameTvShow;
    }

    public JsonNullable<String> getGenre() {
        return genre;
    }

    public JsonNullable<String> getProductionCompany() {
        return productionCompany;
    }
}
