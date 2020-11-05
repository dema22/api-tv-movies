package com.example.spring.controllers;

import com.example.spring.dto.BasicTvShowInfo;
import com.example.spring.models.TvShow;
import com.example.spring.services.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//import com.example.spring.dto.TvShow;

@RestController
@RequestMapping("/tvShow")

public class TvShowController {

    private final TvShowService tvShowService;
    private final RestTemplate restTemplate;

    @Autowired
    public TvShowController(TvShowService tvShowService, RestTemplate restTemplate) {
        this.tvShowService = tvShowService;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/all")
    public void generateDataBase(@RequestBody List<BasicTvShowInfo> listBasicTvShowInfo){
        String apiKey = "e5fa1b7231771db70b84a998344fe4e3";
        RestTemplate restTemplate = new RestTemplate();

        List<TvShow> listTvShow = new ArrayList<>();

        for (BasicTvShowInfo item: listBasicTvShowInfo) {
            TvShow tvShow = restTemplate.getForObject("https://api.themoviedb.org/3/tv/" + item.getId() + "?api_key=" + apiKey + "&language=en-US", TvShow.class);

            // Generating the complete url for the poster path
            tvShow.setPosterPath("https://image.tmdb.org/t/p/original" + tvShow.getPosterPath());

            tvShowService.addTvShow(tvShow);
        }
    }

    @GetMapping("/")
    public List<TvShow> getAllTvShows(){
        List<TvShow> tvShows = tvShowService.getAllTvShows();
        return tvShows;
    }


    @PostMapping("/")
    public void addTvShow(@RequestBody TvShow tvShow) {
        tvShowService.addTvShow(tvShow);
    }

    /*@GetMapping("/")
    public List<UserTvShow> getAllTvShows(){
        List<UserTvShow> userTvShows = tvShowService.getAllTvShows();
        return userTvShows;
    }

    @DeleteMapping("/{idTvShow}")
    public void deleteTvShow(@PathVariable Integer idTvShow){
        tvShowService.deleteTvShow(idTvShow);
    }*/
}
