package com.example.spring.services;

import com.example.spring.dto.TvShowDetailsDTO;
import com.example.spring.dto.VideoResultsDTO;
import com.example.spring.dto.VideosDTO;
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

    /*
    WORKING GREAT!
    public TvShowDetailsDTO getTvShowDetails(Integer idTvShow) {

        Optional<BasicTvShowInfo> basicTvShowInfo = basicTvShowInfoService.getBasicTvShowInfo(idTvShow);

        RestTemplate restTemplate = new RestTemplate();

        TvShowDetailsDTO tvShowDetailsDTO = restTemplate.getForObject("https://api.themoviedb.org/3/tv/" + idTvShow + "?api_key=" + API_KEY + "&language=en-US", TvShowDetailsDTO.class);

        // Generating the complete url for the poster path
        tvShowDetailsDTO.setPoster_path("https://image.tmdb.org/t/p/original" + tvShowDetailsDTO.getPoster_path());

        return tvShowDetailsDTO;
    }*/


    // I am gonna try to change the url to get more information like images and trailers of the tv show.
    public TvShowDetailsDTO getTvShowDetails(Integer idTvShow) {

        Optional<BasicTvShowInfo> basicTvShowInfo = basicTvShowInfoService.getBasicTvShowInfo(idTvShow);

        RestTemplate restTemplate = new RestTemplate();

        TvShowDetailsDTO tvShowDetailsDTO = restTemplate
                .getForObject("https://api.themoviedb.org/3/tv/" +  idTvShow + "?api_key=" + API_KEY +
                "&language=en-US&append_to_response=videos,images&include_image_language=en,null", TvShowDetailsDTO.class);

        // Generating the complete url for the poster path
        tvShowDetailsDTO.setBackdrop_path("https://image.tmdb.org/t/p/original" + tvShowDetailsDTO.getBackdrop_path());
        tvShowDetailsDTO.setPoster_path("https://image.tmdb.org/t/p/original" + tvShowDetailsDTO.getPoster_path());

        // Loading run time array
        tvShowDetailsDTO.setEpisode_run_time(tvShowDetailsDTO.getEpisode_run_time());

        // Load genres of the show
        tvShowDetailsDTO.setGenres(tvShowDetailsDTO.getGenres());

        // Build the complete url for the videos.
        /*for(VideoResultsDTO video: tvShowDetailsDTO.getVideos().getResults()){
            String youtubeBaseURL = "https://www.youtube.com/watch?v=";
            String vimeoBaseURL = "https://vimeo.com/";

            if( video.getSite() == "YouTube" && video.getType() == "Trailer") {

            }else if( video.getSite() == "Vimeo" && video.getType() == "Trailer"){

            }
        }*/
        return tvShowDetailsDTO;
    }








}
