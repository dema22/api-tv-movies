package com.example.spring.services;

import com.example.spring.dto.TvShowDetailsDTO;
import com.example.spring.models.BasicTvShowInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class TvShowDetailsService {
    private final String API_KEY = "e5fa1b7231771db70b84a998344fe4e3";
    private final RestTemplate restTemplate;
    private final BasicTvShowInfoService basicTvShowInfoService;

    @Autowired
    public TvShowDetailsService(RestTemplate restTemplate, BasicTvShowInfoService basicTvShowInfoService) {
        this.restTemplate = restTemplate;
        this.basicTvShowInfoService = basicTvShowInfoService;
    }

    // We receive the id of a tv show.
    // We search if this id exists in our table basic_tv_show_info.
    // After that, we make a GET request to the external API, to get the full details of the tv show.

    public TvShowDetailsDTO getTvShowDetails(Integer idTvShow) {

        Optional<BasicTvShowInfo> basicTvShowInfo = basicTvShowInfoService.getBasicTvShowInfo(idTvShow);

        RestTemplate restTemplate = new RestTemplate();

        TvShowDetailsDTO tvShowDetailsDTO = restTemplate.getForObject("https://api.themoviedb.org/3/tv/" + idTvShow + "?api_key=" + API_KEY + "&language=en-US", TvShowDetailsDTO.class);

        // Generating the complete url for the poster path
        tvShowDetailsDTO.setPoster_path("https://image.tmdb.org/t/p/original" + tvShowDetailsDTO.getPoster_path());

        return tvShowDetailsDTO;
    }
}
