package com.example.spring.services;

import com.example.spring.dto.GenreDTO;
import com.example.spring.dto.TvShowDetailsDTO;
import com.example.spring.dto.TvShowDetailsResponseDTO;
import com.example.spring.dto.VideoResultsDTO;
import com.example.spring.models.BasicTvShowInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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

    public TvShowDetailsDTO getTvShowDetailsFromAPI(Integer idTvShow) {

        Optional<BasicTvShowInfo> basicTvShowInfo = basicTvShowInfoService.getBasicTvShowInfo(idTvShow);

        RestTemplate restTemplate = new RestTemplate();

        TvShowDetailsDTO tvShowDetailsDTO = restTemplate
                .getForObject("https://api.themoviedb.org/3/tv/" +  idTvShow + "?api_key=" + API_KEY +
                "&language=en-US&append_to_response=videos,images&include_image_language=en,null", TvShowDetailsDTO.class);

        return tvShowDetailsDTO;
    }

    // We are going to call the method that give us the details from the API.
    // We are going to manipulate it to make some changes:
    // * We are going to set the complete image urls
    // * We are going to filter the video results
    // * Also the structure of the object is way simpler because we are going to have less boilerplate information
    // * that we do not want.
    // Finally we return the details of a tv show that the client will use in the UI.

    public TvShowDetailsResponseDTO getTvShowDetails(Integer idTvShow) {

        TvShowDetailsDTO tvShowDetailsDTO = getTvShowDetailsFromAPI(idTvShow);
        TvShowDetailsResponseDTO tvShowDetailsResponseDTO = new TvShowDetailsResponseDTO();

        creatingTvShowDetailsResponseDTO(tvShowDetailsDTO, tvShowDetailsResponseDTO);

        return tvShowDetailsResponseDTO;
    }

    public void creatingTvShowDetailsResponseDTO (TvShowDetailsDTO tvShowDetailsDTO, TvShowDetailsResponseDTO tvShowDetailsResponseDTO) {

        // Mapping the information from the api details to our response details.
        tvShowDetailsResponseDTO.setBackdropPath(tvShowDetailsDTO.getBackdrop_path());
        tvShowDetailsResponseDTO.setPosterPath(tvShowDetailsDTO.getPoster_path());
        buildCompleteImageURLS(tvShowDetailsResponseDTO);
        populateGenreArray(tvShowDetailsDTO, tvShowDetailsResponseDTO);
        filterTrailers(tvShowDetailsDTO, tvShowDetailsResponseDTO);
        tvShowDetailsResponseDTO.setEpisodeRunTime(tvShowDetailsDTO.getEpisode_run_time());
        tvShowDetailsResponseDTO.setName(tvShowDetailsDTO.getName());
        tvShowDetailsResponseDTO.setNumberOfEpisodes(tvShowDetailsDTO.getNumber_of_episodes());
        tvShowDetailsResponseDTO.setNumberOfSeasons(tvShowDetailsDTO.getNumber_of_seasons());
        tvShowDetailsResponseDTO.setOriginalLanguage(tvShowDetailsDTO.getOriginal_language());
        tvShowDetailsResponseDTO.setOverview(tvShowDetailsDTO.getOverview());
        tvShowDetailsResponseDTO.setStatus(tvShowDetailsDTO.getStatus());
        tvShowDetailsResponseDTO.setTagline(tvShowDetailsDTO.getTagline());
        tvShowDetailsResponseDTO.setType(tvShowDetailsDTO.getType());
    }

    // We are going to store each genre name from the API in our details.
    public void populateGenreArray(TvShowDetailsDTO tvShowDetailsDTO, TvShowDetailsResponseDTO tvShowDetailsResponseDTO) {
        List<String> listGenres = new ArrayList<>();

        for(GenreDTO genre: tvShowDetailsDTO.getGenres()){
            listGenres.add(genre.getName());
        }

        tvShowDetailsResponseDTO.setGenres(listGenres);
    }

    public void buildCompleteImageURLS (TvShowDetailsResponseDTO tvShowDetailsResponseDTO) {
        String baseImageURL = "https://image.tmdb.org/t/p/original";
        tvShowDetailsResponseDTO.setBackdropPath(baseImageURL + tvShowDetailsResponseDTO.getBackdropPath());
        tvShowDetailsResponseDTO.setPosterPath(baseImageURL + tvShowDetailsResponseDTO.getPosterPath());
    }

    public void filterTrailers(TvShowDetailsDTO tvShowDetailsDTO, TvShowDetailsResponseDTO tvShowDetailsResponseDTO) {

        List<VideoResultsDTO> listVideoResultsDTO = tvShowDetailsDTO.getVideos().getResults();
        String completeUrl;
        List<String> trailersURL = new ArrayList<>();
        String youtubeBaseURL = "https://www.youtube.com/watch?v=";
        String vimeoBaseURL = "https://vimeo.com/";

        for (VideoResultsDTO video: listVideoResultsDTO) {
            if (video.getSite().equals("YouTube") && video.getType().equals("Trailer")) {
                completeUrl = youtubeBaseURL + video.getKey();
                trailersURL.add(completeUrl);
            } else if (video.getSite().equals("Vimeo") && video.getType().equals("Trailer")) {
                completeUrl = vimeoBaseURL + video.getKey();
                trailersURL.add(completeUrl);
            }
        }
        tvShowDetailsResponseDTO.setTrailersURL(trailersURL);
    }
}
