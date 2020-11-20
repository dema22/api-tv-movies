package com.example.spring.services;

import com.example.spring.dto.TvShowDetailsDTO;
import com.example.spring.dto.TvShowReminderResponseDTO;
import com.example.spring.models.BasicTvShowInfo;
import com.example.spring.models.TvShowReminder;
import com.example.spring.repositories.TvShowReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class TvShowReminderService {

    private final TvShowReminderRepository tvShowReminderRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public TvShowReminderService(TvShowReminderRepository tvShowReminderRepository, RestTemplate restTemplate) {
        this.tvShowReminderRepository = tvShowReminderRepository;
        this.restTemplate = restTemplate;
    }

    public void addTvShowReminder(TvShowReminder tvShowReminder) {
        tvShowReminderRepository.save(tvShowReminder);
    }

    // Get details of a tv show based on a Tv Show Reminder of the user.
    // We will get the id of the object Basic Tv Show Info.
    // Then we will use that id , to make a GET request to the THE MOVIDE DB API where we will
    // get the full details of the tv show.

    public TvShowDetailsDTO getTvShowDetails (TvShowReminder tvShowReminder){
        BasicTvShowInfo basicTvShowInfo = tvShowReminder.getBasicTvShowInfo();

        String apiKey = "e5fa1b7231771db70b84a998344fe4e3";
        RestTemplate restTemplate = new RestTemplate();

        TvShowDetailsDTO tvShowDetailsDTO = restTemplate.getForObject("https://api.themoviedb.org/3/tv/" + basicTvShowInfo.getId() + "?api_key=" + apiKey + "&language=en-US", TvShowDetailsDTO.class);

        // Generating the complete url for the poster path
        tvShowDetailsDTO.setPoster_path("https://image.tmdb.org/t/p/original" + tvShowDetailsDTO.getPoster_path());

        return tvShowDetailsDTO;
    }

    public TvShowReminderResponseDTO getTvShowReminderResponseDTO (Integer idTvShowReminder) {

        // Get the tv show reminder of the user
        Optional<TvShowReminder> tvShowReminder = tvShowReminderRepository.findById(idTvShowReminder);

        // Get current tv show reminder
        TvShowReminder currentTvShowReminder = tvShowReminder.get();

        TvShowDetailsDTO tvShowDetailsDTO = new TvShowDetailsDTO();
        // Get the details of the show.
        if(currentTvShowReminder.getBasicTvShowInfo() != null) {
            tvShowDetailsDTO = this.getTvShowDetails(currentTvShowReminder);
        }

        // Build the Tv Show Reminder Response DTO with all the information we manage to get so far.
        TvShowReminderResponseDTO tvShowReminderResponseDTO = new TvShowReminderResponseDTO();

        tvShowReminderResponseDTO.setUser(currentTvShowReminder.getUser());
        tvShowReminderResponseDTO.setUserTvShow(currentTvShowReminder.getUserTvShow());
        tvShowReminderResponseDTO.setTvShowDetailsDTO(tvShowDetailsDTO);
        tvShowReminderResponseDTO.setCompleted(currentTvShowReminder.getCompleted());
        tvShowReminderResponseDTO.setCurrentSeason(currentTvShowReminder.getCurrentSeason());
        tvShowReminderResponseDTO.setCurrentEpisode(currentTvShowReminder.getCurrentEpisode());
        tvShowReminderResponseDTO.setPersonalRating(currentTvShowReminder.getPersonalRating());

        return tvShowReminderResponseDTO;
    }







    /*// MODIFICO EL METODO TEMPORALMENTE PARA DEOLVER UN TV SHOW DETAILS EN VEZ DE UN TV SHOW REMINDER
    public Optional<TvShowDetails> getTvShowReminder(Integer idTvShowReminder) {

        // Get the tv show reminder of the user
        Optional<TvShowReminder> tvShowReminder = tvShowReminderRepository.findById(idTvShowReminder);

        // Get current tv show reminder
        TvShowReminder currentTvShowReminder = tvShowReminder.get();

        // Get the details of the show.
        TvShowDetails tvShowDetails = this.getTvShowDetails(currentTvShowReminder);


        return Optional.of(tvShowDetails);


        //return tvShowReminderRepository.findById(idTvShowReminder);
    }*/

    public List<TvShowReminder> getAllTvShowsReminder() {
        return tvShowReminderRepository.findAll();
    }

    public void deleteTvShowReminder(Integer idTvShowReminder) {
        tvShowReminderRepository.deleteById(idTvShowReminder);
    }
}
