package com.example.spring.services;

import com.example.spring.dto.TvShowDetailsResponseDTO;
import com.example.spring.dto.TvShowReminderPatchDTO;
import com.example.spring.dto.TvShowReminderResponseDTO;
import com.example.spring.models.BasicTvShowInfo;
import com.example.spring.models.TvShowReminder;
import com.example.spring.repositories.TvShowReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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

    // Get a tv show reminder dto by its id.
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

        tvShowReminderResponseDTO.setIdTvShowReminder(currentTvShowReminder.getIdTvShowReminder());
        tvShowReminderResponseDTO.setUser(currentTvShowReminder.getUser());
        tvShowReminderResponseDTO.setUserTvShow(currentTvShowReminder.getUserTvShow());
        tvShowReminderResponseDTO.setTvShowDetailsResponseDTO(tvShowDetailsResponseDTO);
        tvShowReminderResponseDTO.setCompleted(currentTvShowReminder.getCompleted());
        tvShowReminderResponseDTO.setCurrentSeason(currentTvShowReminder.getCurrentSeason());
        tvShowReminderResponseDTO.setCurrentEpisode(currentTvShowReminder.getCurrentEpisode());
        tvShowReminderResponseDTO.setPersonalRating(currentTvShowReminder.getPersonalRating());

        return tvShowReminderResponseDTO;
    }

    // Get all tv show reminder DTO based on the user id.
    public List<TvShowReminderResponseDTO> getAllTvShowsReminderDTO (Integer idUser) {

        // Get all the tv show reminder of the user
        List<TvShowReminder> tvShowRemindersList = getAllTvShowsReminderEntities(idUser);

        // Create a list of the tv show reminders DTO
        List<TvShowReminderResponseDTO> tvShowReminderListDTO = new ArrayList<>();

        for(TvShowReminder tvShowReminder: tvShowRemindersList) {

            // Get the basic tv show info object
            BasicTvShowInfo basicTvShowInfo = tvShowReminder.getBasicTvShowInfo();

            // Create the dto object for the details of the tv show
            TvShowDetailsResponseDTO tvShowDetailsResponseDTO = new TvShowDetailsResponseDTO();

            // Get the details of the show.
            if (basicTvShowInfo != null) {

                if (basicTvShowInfo.getId() != null) {
                    tvShowDetailsResponseDTO = tvShowDetailsService.getTvShowDetails(basicTvShowInfo.getId());
                }
            }

            // Build the Tv Show Reminder DTO with all the information we manage to get so far.
            TvShowReminderResponseDTO tvShowReminderResponseDTO = new TvShowReminderResponseDTO();
            tvShowReminderResponseDTO.setIdTvShowReminder(tvShowReminder.getIdTvShowReminder());
            tvShowReminderResponseDTO.setUser(tvShowReminder.getUser());
            tvShowReminderResponseDTO.setUserTvShow(tvShowReminder.getUserTvShow());
            tvShowReminderResponseDTO.setTvShowDetailsResponseDTO(tvShowDetailsResponseDTO);
            tvShowReminderResponseDTO.setCompleted(tvShowReminder.getCompleted());
            tvShowReminderResponseDTO.setCurrentSeason(tvShowReminder.getCurrentSeason());
            tvShowReminderResponseDTO.setCurrentEpisode(tvShowReminder.getCurrentEpisode());
            tvShowReminderResponseDTO.setPersonalRating(tvShowReminder.getPersonalRating());

            // We add the reminder dto to the list
            tvShowReminderListDTO.add(tvShowReminderResponseDTO);
        }

        return tvShowReminderListDTO;
    }

    // Get all the show reminders entity searching by the user id.
    public List<TvShowReminder> getAllTvShowsReminderEntities (Integer idUser) {
        return tvShowReminderRepository.findByUser_IdUser(idUser);
    }

    // Delete a tv show reminder.
    public void deleteTvShowReminder(Integer idTvShowReminder) {
        tvShowReminderRepository.deleteById(idTvShowReminder);
    }

    // Get a tv show reminder entity by its id
    public Optional<TvShowReminder> getTvShowReminder (Integer idTvShowReminder) {
        return tvShowReminderRepository.findById(idTvShowReminder);
    }

    public void updateTvShowReminder(TvShowReminderPatchDTO tvShowReminderToUpdate, Integer idTvShowReminder) {
        Optional<TvShowReminder> tvShowReminder = getTvShowReminder(idTvShowReminder);
        TvShowReminder currentTvShowReminder = tvShowReminder.get();

        if(tvShowReminderToUpdate.getCompleted() != null) {
            if (tvShowReminderToUpdate.getCompleted().isPresent()) {
                currentTvShowReminder.setCompleted(tvShowReminderToUpdate.getCompleted().get());
            } else {
                currentTvShowReminder.setCompleted(null);
            }
        }

        if(tvShowReminderToUpdate.getCurrentSeason() != null) {
            if (tvShowReminderToUpdate.getCurrentSeason().isPresent()) {
                currentTvShowReminder.setCurrentSeason(tvShowReminderToUpdate.getCurrentSeason().get());
            } else {
                currentTvShowReminder.setCurrentSeason(null);
            }
        }

        if(tvShowReminderToUpdate.getCurrentEpisode() != null) {
            if (tvShowReminderToUpdate.getCurrentEpisode().isPresent()) {
                currentTvShowReminder.setCurrentEpisode(tvShowReminderToUpdate.getCurrentEpisode().get());
            } else {
                currentTvShowReminder.setCurrentEpisode(null);
            }
        }

        if(tvShowReminderToUpdate.getPersonalRating() != null) {
            if (tvShowReminderToUpdate.getPersonalRating().isPresent()) {
                currentTvShowReminder.setPersonalRating(tvShowReminderToUpdate.getPersonalRating().get());
            } else {
                currentTvShowReminder.setPersonalRating(null);
            }
        }

        tvShowReminderRepository.save(currentTvShowReminder);
    }
}
