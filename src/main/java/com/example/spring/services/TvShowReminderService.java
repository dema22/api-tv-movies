package com.example.spring.services;

import com.example.spring.dto.TvShowDetailsResponseDTO;
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
    private final TvShowDetailsService tvShowDetailsService;
    private final TvShowReminderRepository tvShowReminderRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public TvShowReminderService(TvShowDetailsService tvShowDetailsService, TvShowReminderRepository tvShowReminderRepository, RestTemplate restTemplate) {
        this.tvShowDetailsService = tvShowDetailsService;
        this.tvShowReminderRepository = tvShowReminderRepository;
        this.restTemplate = restTemplate;
    }

    public void addTvShowReminder(TvShowReminder tvShowReminder) {
        tvShowReminderRepository.save(tvShowReminder);
    }

    public TvShowReminderResponseDTO getTvShowReminderResponseDTO (Integer idTvShowReminder) {

        // Get the tv show reminder of the user
        Optional<TvShowReminder> tvShowReminder = tvShowReminderRepository.findById(idTvShowReminder);

        // Get current tv show reminder
        TvShowReminder currentTvShowReminder = tvShowReminder.get();

        // Get the basic tv show info object
        BasicTvShowInfo basicTvShowInfo = currentTvShowReminder.getBasicTvShowInfo();

        TvShowDetailsResponseDTO tvShowDetailsResponseDTO = new TvShowDetailsResponseDTO();

        // Get the details of the show.
        if(basicTvShowInfo != null) {

            if (basicTvShowInfo.getId() != null) {
                tvShowDetailsResponseDTO = tvShowDetailsService.getTvShowDetails(basicTvShowInfo.getId());
            }
        }

        // Build the Tv Show Reminder Response DTO with all the information we manage to get so far.
        TvShowReminderResponseDTO tvShowReminderResponseDTO = new TvShowReminderResponseDTO();

        tvShowReminderResponseDTO.setUser(currentTvShowReminder.getUser());
        tvShowReminderResponseDTO.setUserTvShow(currentTvShowReminder.getUserTvShow());
        tvShowReminderResponseDTO.setTvShowDetailsResponseDTO(tvShowDetailsResponseDTO);
        tvShowReminderResponseDTO.setCompleted(currentTvShowReminder.getCompleted());
        tvShowReminderResponseDTO.setCurrentSeason(currentTvShowReminder.getCurrentSeason());
        tvShowReminderResponseDTO.setCurrentEpisode(currentTvShowReminder.getCurrentEpisode());
        tvShowReminderResponseDTO.setPersonalRating(currentTvShowReminder.getPersonalRating());

        return tvShowReminderResponseDTO;
    }

    public List<TvShowReminder> getAllTvShowsReminder() {
        return tvShowReminderRepository.findAll();
    }

    public void deleteTvShowReminder(Integer idTvShowReminder) {
        tvShowReminderRepository.deleteById(idTvShowReminder);
    }
}
