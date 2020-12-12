package com.example.spring.services;

import com.example.spring.dto.*;
import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.models.BasicTvShowInfo;
import com.example.spring.models.TvShowReminder;
import com.example.spring.repositories.TvShowReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TvShowReminderService {
    private final TvShowDetailsService tvShowDetailsService;
    private final TvShowReminderRepository tvShowReminderRepository;

    @Autowired
    public TvShowReminderService(TvShowDetailsService tvShowDetailsService, TvShowReminderRepository tvShowReminderRepository) {
        this.tvShowDetailsService = tvShowDetailsService;
        this.tvShowReminderRepository = tvShowReminderRepository;
    }

    public void addTvShowReminder(TvShowReminder tvShowReminder) {
        tvShowReminderRepository.save(tvShowReminder);
    }

    // Get a tv show reminder dto by its id.
    public TvShowReminderResponseDTO getTvShowReminderResponseDTO (Integer idTvShowReminder) throws ResourceNotFoundException {

        // Get the tv show reminder of the user
        Optional<TvShowReminder> tvShowReminder = tvShowReminderRepository.findById(idTvShowReminder);

        // Get current tv show reminder
        TvShowReminder currentTvShowReminder = tvShowReminder.get();

        // Get the basic tv show info object
        BasicTvShowInfo basicTvShowInfo = currentTvShowReminder.getBasicTvShowInfo();

        TvShowDetailsResponseDTO tvShowDetailsResponseDTO = null;

        // Get the details of the show.
        if(basicTvShowInfo != null) {

            if (basicTvShowInfo.getId() != null) {
                tvShowDetailsResponseDTO = tvShowDetailsService.getTvShowDetails(basicTvShowInfo.getId());
            }
        }
        // Build the Tv Show Reminder Response DTO with all the information we manage to get so far.
        TvShowReminderResponseDTO tvShowReminderResponseDTO = new TvShowReminderResponseDTO();
        buildTvShowReminderDTO(tvShowReminderResponseDTO, currentTvShowReminder, tvShowDetailsResponseDTO);

        return tvShowReminderResponseDTO;
    }

    // Get all tv show reminder DTO based on the user id.
    public List<TvShowReminderResponseDTO> getAllTvShowsReminderDTO (Integer idUser) throws ResourceNotFoundException {

        // Get all the tv show reminder of the user.
        List<TvShowReminder> tvShowRemindersList = getAllTvShowsReminderEntities(idUser);

        // Create a list of the tv show reminders DTO
        List<TvShowReminderResponseDTO> tvShowReminderListDTO = new ArrayList<>();

        for(TvShowReminder tvShowReminder: tvShowRemindersList) {

            // Get the basic tv show info object
            BasicTvShowInfo basicTvShowInfo = tvShowReminder.getBasicTvShowInfo();

            // Create the dto object for the details of the tv show
            TvShowDetailsResponseDTO tvShowDetailsResponseDTO = null;

            // Get the details of the show.
            if (basicTvShowInfo != null) {

                if (basicTvShowInfo.getId() != null) {
                    tvShowDetailsResponseDTO = tvShowDetailsService.getTvShowDetails(basicTvShowInfo.getId());
                }
            }

            // Build the Tv Show Reminder DTO with all the information we manage to get so far.
            TvShowReminderResponseDTO tvShowReminderResponseDTO = new TvShowReminderResponseDTO();
            buildTvShowReminderDTO(tvShowReminderResponseDTO, tvShowReminder, tvShowDetailsResponseDTO);

            // We add the reminder dto to the list
            tvShowReminderListDTO.add(tvShowReminderResponseDTO);
        }

        return tvShowReminderListDTO;
    }

    public void buildTvShowReminderDTO (TvShowReminderResponseDTO tvShowReminderResponseDTO,
                                        TvShowReminder currentTvShowReminder,
                                        TvShowDetailsResponseDTO tvShowDetailsResponseDTO) {

        tvShowReminderResponseDTO.setIdTvShowReminder(currentTvShowReminder.getIdTvShowReminder());
        tvShowReminderResponseDTO.setUser(currentTvShowReminder.getUser());
        tvShowReminderResponseDTO.setUserTvShow(currentTvShowReminder.getUserTvShow());

        if(tvShowDetailsResponseDTO != null){
            tvShowReminderResponseDTO.setTvShowDetailsResponseDTO(tvShowDetailsResponseDTO);
        }else{
            tvShowReminderResponseDTO.setTvShowDetailsResponseDTO(null);
        }

        tvShowReminderResponseDTO.setCompleted(currentTvShowReminder.getCompleted());
        tvShowReminderResponseDTO.setCurrentSeason(currentTvShowReminder.getCurrentSeason());
        tvShowReminderResponseDTO.setCurrentEpisode(currentTvShowReminder.getCurrentEpisode());
        tvShowReminderResponseDTO.setPersonalRating(currentTvShowReminder.getPersonalRating());
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
        Optional<TvShowReminder> optionalTvShowReminder = getTvShowReminder(idTvShowReminder);

        // If optional not present throw an exception
        /*if (!optionalTvShowReminder.isPresent()) {
            return new Exception();
        }¨*/

        TvShowReminder currentTvShowReminder = optionalTvShowReminder.get();

        if (tvShowReminderToUpdate.getCompleted().isPresent()) {
            currentTvShowReminder.setCompleted(tvShowReminderToUpdate.getCompleted().get());
        }

        if (tvShowReminderToUpdate.getCurrentSeason().isPresent()) {
            currentTvShowReminder.setCurrentSeason(tvShowReminderToUpdate.getCurrentSeason().get());
        }

        if (tvShowReminderToUpdate.getCurrentEpisode().isPresent()) {
            currentTvShowReminder.setCurrentEpisode(tvShowReminderToUpdate.getCurrentEpisode().get());
        }

        if (tvShowReminderToUpdate.getPersonalRating().isPresent()) {
            currentTvShowReminder.setPersonalRating(tvShowReminderToUpdate.getPersonalRating().get());
        }

        tvShowReminderRepository.save(currentTvShowReminder);
    }


    // Add a new method to return a paginated list of tv show reminders
    public Page<TvShowReminder> getPaginatedTvShowReminders(Integer page, Integer size, Integer idUser) {
        // Sort.by(Sort.Direction.ASC, "basicTvShowInfo.originalName")
        return tvShowReminderRepository.findByUser_IdUser(PageRequest.of(page,size),idUser);
    }

    public PageTvShowRemindersResponseDTO getPaginatedTvShowReminderResponseDTO (Integer page, Integer size, Integer idUser) throws ResourceNotFoundException {

        // Get a page of the tv show reminder entity.
        Page<TvShowReminder> pageTvShowReminder = getPaginatedTvShowReminders(page,size,idUser);

        PageTvShowRemindersResponseDTO tvShowReminderResponseDTOS = buildPageTvShowReminderResponseDTO(pageTvShowReminder);

        return tvShowReminderResponseDTOS;
    }

    // We will get a PageTvShowReminderResponseDTO with the information of the tv show reminders DTO in a list AND
    // the information of the page.
    public PageTvShowRemindersResponseDTO buildPageTvShowReminderResponseDTO (Page pageTvShowReminder) throws ResourceNotFoundException {

        // Get all the tv show reminder of the user.
        List<TvShowReminder> tvShowRemindersList = pageTvShowReminder.getContent();

        // Create a list of the tv show reminders DTO
        List<TvShowReminderResponseDTO> tvShowReminderListDTO = new ArrayList<>();

        for(TvShowReminder tvShowReminder: tvShowRemindersList) {

            // Get the basic tv show info object
            BasicTvShowInfo basicTvShowInfo = tvShowReminder.getBasicTvShowInfo();

            // Create the dto object for the details of the tv show
            TvShowDetailsResponseDTO tvShowDetailsResponseDTO = null;

            // Get the details of the show.
            if (basicTvShowInfo != null) {

                if (basicTvShowInfo.getId() != null) {
                    tvShowDetailsResponseDTO = tvShowDetailsService.getTvShowDetails(basicTvShowInfo.getId());
                }
            }

            // Build the Tv Show Reminder DTO with all the information we manage to get so far.
            TvShowReminderResponseDTO tvShowReminderResponseDTO = new TvShowReminderResponseDTO();
            buildTvShowReminderDTO(tvShowReminderResponseDTO, tvShowReminder, tvShowDetailsResponseDTO);

            // We add the reminder dto to the list
            tvShowReminderListDTO.add(tvShowReminderResponseDTO);
        }

        // Build the page to return the list of tv show reminders DTO and description of the page.
        PageTvShowRemindersResponseDTO<TvShowReminderResponseDTO> pageTvShowReminderResponseDTO = new PageTvShowRemindersResponseDTO<>();
        buildPage(pageTvShowReminderResponseDTO, tvShowReminderListDTO, pageTvShowReminder);

        return pageTvShowReminderResponseDTO;
    }

    private void buildPage(PageTvShowRemindersResponseDTO<TvShowReminderResponseDTO> page,
                           List<TvShowReminderResponseDTO> tvShowReminderListDTO,
                           Page pageTvShowReminder) {
        page.setTvShowRemindersResponseDTO(tvShowReminderListDTO);
        PageDescriptionDTO pageDescription = new PageDescriptionDTO();
        pageDescription.setTotalPages(pageTvShowReminder.getTotalPages()); // For the  number of total pages.
        pageDescription.setTotalElements(pageTvShowReminder.getTotalElements());// For total items stored in database.
        pageDescription.setNumberOfElementsReturn(pageTvShowReminder.getNumberOfElements());// Total elements we return on the page.
        page.setPageDescriptionDTO(pageDescription);
    }
}
