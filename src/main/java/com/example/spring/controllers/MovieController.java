package com.example.spring.controllers;

import com.example.spring.dto.BasicMovieInfoDTO;
import com.example.spring.services.BasicMovieInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/movies")

public class MovieController {
    private BasicMovieInfoService basicMovieInfoService;

    @Autowired
    public MovieController(BasicMovieInfoService basicMovieInfoService) {
        this.basicMovieInfoService = basicMovieInfoService;
    }

    @PostMapping("/loadMovieTable")
    public void generateMovieTable(@RequestBody List<BasicMovieInfoDTO> listBasicMovieInfoDTO) {
        basicMovieInfoService.saveAllMoviesFromAPIFile(listBasicMovieInfoDTO);
    }
}
