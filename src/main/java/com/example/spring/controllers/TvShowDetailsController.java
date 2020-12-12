package com.example.spring.controllers;

import com.example.spring.dto.TvShowDetailsResponseDTO;
import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.services.TvShowDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tvShowDetails")

public class TvShowDetailsController {
    private final TvShowDetailsService tvShowDetailsService;

    @Autowired
    public TvShowDetailsController(TvShowDetailsService tvShowDetailsService) {
        this.tvShowDetailsService = tvShowDetailsService;
    }

    @GetMapping("/{idTvShow}")
    public TvShowDetailsResponseDTO getTvShowDetails (@PathVariable Integer idTvShow) throws ResourceNotFoundException {
        return tvShowDetailsService.getTvShowDetails(idTvShow);
    }
}
