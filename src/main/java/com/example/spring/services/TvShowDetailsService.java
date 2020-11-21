package com.example.spring.services;

import com.example.spring.dto.TvShowDetailsDTO;
import com.example.spring.dto.VideoResultsDTO;
import com.example.spring.dto.VideosDTO;
import com.example.spring.models.BasicTvShowInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;
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

        TvShowDetailsDTO tvShowDetailsDTO = restTemplate
                .getForObject("https://api.themoviedb.org/3/tv/" +  idTvShow + "?api_key=" + API_KEY +
                "&language=en-US&append_to_response=videos,images&include_image_language=en,null", TvShowDetailsDTO.class);

        // Generating the complete url for images
        buildCompleteImageURLS(tvShowDetailsDTO);

        // We will keep only the type of videos that are trailers.
        filterTrailers(tvShowDetailsDTO.getVideos());

        return tvShowDetailsDTO;
    }

    // We build the complete url for the images we got from the API.
    public void buildCompleteImageURLS (TvShowDetailsDTO tvShowDetailsDTO) {
        String baseImageURL = "https://image.tmdb.org/t/p/original";
        tvShowDetailsDTO.setBackdrop_path(baseImageURL + tvShowDetailsDTO.getBackdrop_path());
        tvShowDetailsDTO.setPoster_path(baseImageURL + tvShowDetailsDTO.getPoster_path());
    }

    // This method will filter the videos results we got from the API.
    // We will remove every video that its not a trailer, and in case we encounter a trailer, we will build the complete URL.
    public void filterTrailers(VideosDTO videos) {

        List<VideoResultsDTO> listVideoResultsDTO = videos.getResults();
        String youtubeBaseURL = "https://www.youtube.com/watch?v=";
        String vimeoBaseURL = "https://vimeo.com/";

        for (Iterator<VideoResultsDTO> iterator = listVideoResultsDTO.iterator(); iterator.hasNext();){
            VideoResultsDTO  video = iterator.next();

            if( video.getSite().equals("YouTube") && video.getType().equals("Trailer")) {
                video.setCompleteUrl(youtubeBaseURL +  video.getKey());
            }else if( video.getSite().equals("Vimeo") && video.getType().equals("Trailer")){
                video.setCompleteUrl(vimeoBaseURL +  video.getKey());
            }else {
                iterator.remove();
            }
        }
    }

}
