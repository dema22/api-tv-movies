package com.example.spring.services;

import com.example.spring.dto.GenreDTO;
import com.example.spring.dto.TvShowDetailsFromApiDTO;
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

    public TvShowDetailsFromApiDTO getTvShowDetailsFromAPI(Integer idTvShow) {

        Optional<BasicTvShowInfo> basicTvShowInfo = basicTvShowInfoService.getBasicTvShowInfo(idTvShow);

        RestTemplate restTemplate = new RestTemplate();

        TvShowDetailsFromApiDTO tvShowDetailsFromApiDTO = restTemplate
                .getForObject("https://api.themoviedb.org/3/tv/" +  idTvShow + "?api_key=" + API_KEY +
                "&language=en-US&append_to_response=videos,images&include_image_language=en,null", TvShowDetailsFromApiDTO.class);

        return tvShowDetailsFromApiDTO;
    }

    // We are going to call the method that give us the details from the API.
    // Create a new DTO based on this information we get from the API.

    public TvShowDetailsResponseDTO getTvShowDetails(Integer idTvShow) {

        TvShowDetailsFromApiDTO tvShowDetailsFromApiDTO = getTvShowDetailsFromAPI(idTvShow);
        TvShowDetailsResponseDTO tvShowDetailsResponseDTO = new TvShowDetailsResponseDTO();

        creatingTvShowDetailsResponseDTO(tvShowDetailsFromApiDTO, tvShowDetailsResponseDTO);

        return tvShowDetailsResponseDTO;
    }


    // Mapped the information from the tv show details from the api into another DTO details.
    // We also we are going to call some methods to:
    // * Set the complete image urls.
    // * Filter the video results.

    public void creatingTvShowDetailsResponseDTO (TvShowDetailsFromApiDTO tvShowDetailsFromApiDTO, TvShowDetailsResponseDTO tvShowDetailsResponseDTO) {
        tvShowDetailsResponseDTO.setBackdropPath(tvShowDetailsFromApiDTO.getBackdrop_path());
        tvShowDetailsResponseDTO.setPosterPath(tvShowDetailsFromApiDTO.getPoster_path());
        buildCompleteImageURLS(tvShowDetailsResponseDTO);
        populateGenreArray(tvShowDetailsFromApiDTO, tvShowDetailsResponseDTO);
        filterTrailers(tvShowDetailsFromApiDTO, tvShowDetailsResponseDTO);
        tvShowDetailsResponseDTO.setEpisodeRunTime(tvShowDetailsFromApiDTO.getEpisode_run_time());
        tvShowDetailsResponseDTO.setName(tvShowDetailsFromApiDTO.getName());
        tvShowDetailsResponseDTO.setNumberOfEpisodes(tvShowDetailsFromApiDTO.getNumber_of_episodes());
        tvShowDetailsResponseDTO.setNumberOfSeasons(tvShowDetailsFromApiDTO.getNumber_of_seasons());
        tvShowDetailsResponseDTO.setOriginalLanguage(tvShowDetailsFromApiDTO.getOriginal_language());
        tvShowDetailsResponseDTO.setOverview(tvShowDetailsFromApiDTO.getOverview());
        tvShowDetailsResponseDTO.setStatus(tvShowDetailsFromApiDTO.getStatus());
        tvShowDetailsResponseDTO.setTagline(tvShowDetailsFromApiDTO.getTagline());
        tvShowDetailsResponseDTO.setType(tvShowDetailsFromApiDTO.getType());
    }

    // We are going to store each genre name we get from the API in our tv show DTO response.
    public void populateGenreArray(TvShowDetailsFromApiDTO tvShowDetailsFromApiDTO, TvShowDetailsResponseDTO tvShowDetailsResponseDTO) {
        List<String> listGenres = new ArrayList<>();

        for(GenreDTO genre: tvShowDetailsFromApiDTO.getGenres()){
            listGenres.add(genre.getName());
        }

        tvShowDetailsResponseDTO.setGenres(listGenres);
    }

    public void buildCompleteImageURLS (TvShowDetailsResponseDTO tvShowDetailsResponseDTO) {
        String baseImageURL = "https://image.tmdb.org/t/p/original";
        tvShowDetailsResponseDTO.setBackdropPath(baseImageURL + tvShowDetailsResponseDTO.getBackdropPath());
        tvShowDetailsResponseDTO.setPosterPath(baseImageURL + tvShowDetailsResponseDTO.getPosterPath());
    }

    // We are going to filter all the videos that are trailers and build their complete url to that video.
    // We are going to store each complete url in a list.
    // Finally, store this list in the tvShowDetailsResponseDTO object.

    public void filterTrailers(TvShowDetailsFromApiDTO tvShowDetailsFromApiDTO, TvShowDetailsResponseDTO tvShowDetailsResponseDTO) {

        List<VideoResultsDTO> listVideoResultsDTO = tvShowDetailsFromApiDTO.getVideos().getResults();
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
