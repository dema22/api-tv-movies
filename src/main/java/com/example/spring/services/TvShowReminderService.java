package com.example.spring.services;

import com.example.spring.dto.*;
import com.example.spring.exception.BusinessLogicValidationFailure;
import com.example.spring.exception.ResourceAlreadyExistsException;
import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.models.BasicTvShowInfo;
import com.example.spring.models.TvShowReminder;
import com.example.spring.models.User;
import com.example.spring.models.UserTvShow;
import com.example.spring.repositories.TvShowReminderRepository;
import com.example.spring.repositories.UserTvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TvShowReminderService {
    private final UserTvShowService userTvShowService;
    private final TvShowDetailsService tvShowDetailsService;
    private final TvShowReminderRepository tvShowReminderRepository;
    private final UserTvShowRepository userTvShowRepository;

    @Autowired
    public TvShowReminderService(UserTvShowService userTvShowService, TvShowDetailsService tvShowDetailsService, TvShowReminderRepository tvShowReminderRepository, UserTvShowRepository userTvShowRepository) {
        this.userTvShowService = userTvShowService;
        this.tvShowDetailsService = tvShowDetailsService;
        this.tvShowReminderRepository = tvShowReminderRepository;
        this.userTvShowRepository = userTvShowRepository;
    }

    // Done
    public void addTvShowReminder(TvShowReminder tvShowReminder) throws ResourceAlreadyExistsException, BusinessLogicValidationFailure, ResourceNotFoundException {
        validateExistenceOfTvShowReminder(tvShowReminder);
        tvShowReminderRepository.save(tvShowReminder);
    }

    // Done
    private void validateExistenceOfTvShowReminder(TvShowReminder tvShowReminder) throws ResourceAlreadyExistsException, BusinessLogicValidationFailure, ResourceNotFoundException {
        Optional<TvShowReminder> tvShowReminderOptional = Optional.empty();
        String messageError = null;

        // First i validate if the user tv show we want to add to the reminder belongs to the logged user.
        //userTvShowService.getUserTvShow(tvShowReminder.getUser().getIdUser(), tvShowReminder.getUserTvShow().getIdTvShowCreatedByUser());

        // If both the basic tv show info object and user tv show object ARE PRESENT -> exception
        // If the basic tv show info object id already exists in the reminders table -> exception
        // If the user tv show info object id already exists in the reminders table -> exception
        // If both are null -> exception.

        if(tvShowReminder.getBasicTvShowInfo() != null && tvShowReminder.getUserTvShow() != null){

            if(tvShowReminder.getBasicTvShowInfo().getId() != null && tvShowReminder.getUserTvShow().getIdTvShowCreatedByUser() != null)
                throw new BusinessLogicValidationFailure("You cant have a reminder that point to a tv show from the system and to a tv show created by the user");

        }else if(tvShowReminder.getBasicTvShowInfo() != null){

            if(tvShowReminder.getBasicTvShowInfo().getId() != null) {
                tvShowReminderOptional = tvShowReminderRepository.findByUserIdAndTvShowId(tvShowReminder.getUser().getIdUser(), tvShowReminder.getBasicTvShowInfo().getId());
                messageError = "User already created a tv show reminder with the basicTvShowInfo id : " + tvShowReminder.getBasicTvShowInfo().getId();
            }

        }else if (tvShowReminder.getUserTvShow() != null){

            if(tvShowReminder.getUserTvShow().getIdTvShowCreatedByUser() != null) {

                // Validate if the user tv show of the reminder actually belong to the logged user.
                if(Optional.ofNullable(userTvShowService.getUserTvShow(tvShowReminder.getUser().getIdUser(), tvShowReminder.getUserTvShow().getIdTvShowCreatedByUser())).isPresent()) {

                    tvShowReminderOptional = tvShowReminderRepository.findByUserIdAndTvShowCreatedByUserId(tvShowReminder.getUser().getIdUser(), tvShowReminder.getUserTvShow().getIdTvShowCreatedByUser());
                    messageError = "User already created a tv show reminder with a userTvShow id : " + tvShowReminder.getUserTvShow().getIdTvShowCreatedByUser();
                }
            }

        }else{
            messageError = "To create a tv show reminder you have to provided a basicTvShowInfo id OR a userTvShow id";
            throw new BusinessLogicValidationFailure(messageError);
        }

        // Each query findByUserIdAndTvShowId and findByUserIdAndTvShowCreatedByUserId return an optional with the tv show or an empty optional.
        // This method will return true if there is a tv show present in the optional OR returns false if is an empty optional (with null value).
        if(tvShowReminderOptional.isPresent()){
            throw new ResourceAlreadyExistsException(messageError);
        }
    }

    // Done
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

    // Done
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

    // Done
    public void buildTvShowReminderDTO (TvShowReminderResponseDTO tvShowReminderResponseDTO,
                                        TvShowReminder currentTvShowReminder,
                                        TvShowDetailsResponseDTO tvShowDetailsResponseDTO) {

        tvShowReminderResponseDTO.setIdTvShowReminder(currentTvShowReminder.getIdTvShowReminder());

        if(currentTvShowReminder.getUserTvShow() != null){
            UserTvShowDTO userTvShowDTO = new UserTvShowDTO();
            userTvShowService.buildUserTvShowDTO(currentTvShowReminder.getUserTvShow(), userTvShowDTO);
            tvShowReminderResponseDTO.setUserTvShowDTO(userTvShowDTO);
        }else{
            tvShowReminderResponseDTO.setUserTvShowDTO(null);
        }

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

    // Done
    // Get all the show reminders entity searching by the user id.
    public List<TvShowReminder> getAllTvShowsReminderEntities (Integer idUser) {
        return tvShowReminderRepository.findByUser_IdUser(idUser);
    }

    // Delete a tv show reminder.
    public void deleteTvShowReminder(Integer idTvShowReminder) {
        tvShowReminderRepository.deleteById(idTvShowReminder);
    }

    // Done
    // Get a tv show reminder entity by its id and user id.
    public TvShowReminder getTvShowReminder (Integer idUser, Integer idTvShowReminder) throws ResourceNotFoundException {
        return tvShowReminderRepository.findByIdTvShowReminderAndUserId(idUser, idTvShowReminder).orElseThrow(() -> new ResourceNotFoundException("The user tv show reminder with the id : " + idTvShowReminder + " was not found."));
    }

    // Done
    public void updateTvShowReminder(Integer idUser, TvShowReminderPatchDTO tvShowReminderToUpdate, Integer idTvShowReminder) throws ResourceNotFoundException {
        TvShowReminder currentTvShowReminder = getTvShowReminder(idUser, idTvShowReminder);

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

    // Done
    // Add a new method to return a paginated list of tv show reminders
    public Page<TvShowReminder> getPaginatedTvShowReminders(Integer page, Integer size, Integer idUser) {
        // Sort.by(Sort.Direction.ASC, "basicTvShowInfo.originalName")
        return tvShowReminderRepository.findByUser_IdUser(PageRequest.of(page,size),idUser);
    }

    // Done
    public PageTvShowRemindersResponseDTO getPaginatedTvShowReminderResponseDTO (Integer page, Integer size, Integer idUser) throws ResourceNotFoundException {

        // Get a page of the tv show reminder entity.
        Page<TvShowReminder> pageTvShowReminder = getPaginatedTvShowReminders(page,size,idUser);

        PageTvShowRemindersResponseDTO tvShowReminderResponseDTOS = buildPageTvShowReminderResponseDTO(pageTvShowReminder);

        return tvShowReminderResponseDTOS;
    }

    // Done
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

    // Done
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
