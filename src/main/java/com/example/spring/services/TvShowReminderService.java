package com.example.spring.services;

import com.example.spring.dto.*;
import com.example.spring.exception.BusinessLogicValidationFailure;
import com.example.spring.exception.ResourceAlreadyExistsException;
import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.models.BasicTvShowInfo;
import com.example.spring.models.TvShowReminder;
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

    public TvShowReminderResponseDTO addTvShowReminder(Integer idLoggedUser, TvShowReminder tvShowReminder) throws ResourceAlreadyExistsException, BusinessLogicValidationFailure, ResourceNotFoundException {
        if(idLoggedUser == tvShowReminder.getUser().getIdUser()) {
            validateTvShowReminder(tvShowReminder);
            tvShowReminderRepository.save(tvShowReminder);
            // Once the reminder was saved, I query to get a DTO of this reminder with extra info like its details.
            return getTvShowReminderDTO(tvShowReminder);
        }else
            throw new BusinessLogicValidationFailure("The current logged user CANT add a tv show reminder to another user account.");
    }

    private void validateTvShowReminder(TvShowReminder tvShowReminder) throws ResourceAlreadyExistsException, BusinessLogicValidationFailure, ResourceNotFoundException {
        validationExistenceOfTvShowsInReminder(tvShowReminder);
        validateAbsenceOfTvShowsInReminder(tvShowReminder);
        validateDuplicateBasicTvShowInfoInReminders(tvShowReminder);
    }

    // If the basic tv show info object id already exists in the reminders table -> exception
    private void validateDuplicateBasicTvShowInfoInReminders(TvShowReminder tvShowReminder) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        Optional<TvShowReminder> tvShowReminderOptional = Optional.empty();
        String messageError = null;

        // If already exists a reminder with the id BasicTvShowInfo we are looking for : exception
        if(tvShowReminder.getBasicTvShowInfo() != null){
            if(tvShowReminder.getBasicTvShowInfo().getId() != null) {
                tvShowReminderOptional = tvShowReminderRepository.findByUserIdAndTvShowId(tvShowReminder.getUser().getIdUser(), tvShowReminder.getBasicTvShowInfo().getId());
                messageError = "User already created a tv show reminder with the basicTvShowInfo id : " + tvShowReminder.getBasicTvShowInfo().getId();
            }
        }

        // If already exists a reminder with the id UserTvShow we are looking for : exception
        // Validate if the user tv show of the reminder actually belong to the logged user.
        if (tvShowReminder.getUserTvShow() != null){
            if(tvShowReminder.getUserTvShow().getIdTvShowCreatedByUser() != null) {
                if(Optional.ofNullable(userTvShowService.getUserTvShow(tvShowReminder.getUser().getIdUser(), tvShowReminder.getUserTvShow().getIdTvShowCreatedByUser())).isPresent()) { // Validate if the user tv show of the reminder actually belong to the logged user.
                    tvShowReminderOptional = tvShowReminderRepository.findByUserIdAndTvShowCreatedByUserId(tvShowReminder.getUser().getIdUser(), tvShowReminder.getUserTvShow().getIdTvShowCreatedByUser());
                    messageError = "User already created a tv show reminder with a userTvShow id : " + tvShowReminder.getUserTvShow().getIdTvShowCreatedByUser();
                }
            }
        }

        // Each query findByUserIdAndTvShowId and findByUserIdAndTvShowCreatedByUserId return an optional with the tv show or an empty optional.
        // This method will return true if there is a tv show present in the optional OR returns false if is an empty optional (with null value).
        if(tvShowReminderOptional.isPresent()){
            throw new ResourceAlreadyExistsException(messageError);
        }
    }

    // If there is a userTvShow and a BasicTvShow OR both are null means we have an exception
    public void validationExistenceOfTvShowsInReminder(TvShowReminder tvShowReminder) throws BusinessLogicValidationFailure {
        if(tvShowReminder.getBasicTvShowInfo() != null && tvShowReminder.getUserTvShow() != null) {
            if (tvShowReminder.getBasicTvShowInfo().getId() != null && tvShowReminder.getUserTvShow().getIdTvShowCreatedByUser() != null)
                throw new BusinessLogicValidationFailure("You cant have a reminder that point to a tv show from the system and to a tv show created by the user");
        }
    }

    // If there isnt a userTvShow or a basicTvShow means we have an exception
    public void validateAbsenceOfTvShowsInReminder(TvShowReminder tvShowReminder) throws BusinessLogicValidationFailure {
        String messageError = "To create a tv show reminder you have to provided a basicTvShowInfo id OR a userTvShow id";

        if(tvShowReminder.getBasicTvShowInfo() == null && tvShowReminder.getUserTvShow() == null) {
            throw new BusinessLogicValidationFailure(messageError);
        }else if(tvShowReminder.getBasicTvShowInfo() != null && tvShowReminder.getUserTvShow() != null) { // Check for the existence of the object
            if(tvShowReminder.getBasicTvShowInfo().getId() == null && tvShowReminder.getUserTvShow().getIdTvShowCreatedByUser() == null) // check if the ids are null
                throw new BusinessLogicValidationFailure(messageError);
        }
    }

    // Done
    public TvShowReminderResponseDTO getTvShowReminderDTO(TvShowReminder tvShowReminder) throws ResourceNotFoundException {
        BasicTvShowInfo basicTvShowInfo = tvShowReminder.getBasicTvShowInfo();
        TvShowDetailsResponseDTO tvShowDetailsResponseDTO = new TvShowDetailsResponseDTO();
        TvShowReminderResponseDTO tvShowReminderDTO = new TvShowReminderResponseDTO();

        if(basicTvShowInfo != null) {
            if(basicTvShowInfo.getId() != null) {
                tvShowDetailsResponseDTO = tvShowDetailsService.getTvShowDetails(basicTvShowInfo.getId());
            }
        }

        // Build the Tv Show Reminder DTO with all the information we manage to get so far.
        buildTvShowReminderDTO(tvShowReminderDTO, tvShowReminder, tvShowDetailsResponseDTO);

        return tvShowReminderDTO;
    }

    // Done
    // Get all tv show reminder DTO based on the user id.
    public List<TvShowReminderResponseDTO> getAllTvShowsReminderDTO (Integer idUser) throws ResourceNotFoundException {

        List<TvShowReminder> tvShowRemindersList = getAllTvShowsReminderEntities(idUser);
        List<TvShowReminderResponseDTO> tvShowReminderListDTO = new ArrayList<>();

        for(TvShowReminder tvShowReminder: tvShowRemindersList) {

            BasicTvShowInfo basicTvShowInfo = tvShowReminder.getBasicTvShowInfo();
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

    // Done
    // Delete a tv show reminder by its id. We check if it belongs to the logged user first.
    public void deleteTvShowReminder(Integer idUser, Integer idTvShowReminder) throws ResourceNotFoundException {
        if(getTvShowReminder(idUser,idTvShowReminder) != null){
            tvShowReminderRepository.deleteById(idTvShowReminder);
        }
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
    // Add a new method to return a paginated list of tv show reminders entity
    public Page<TvShowReminder> getPaginatedTvShowReminders(Integer page, Integer size, Integer idUser) {
        return tvShowReminderRepository.findByUser_IdUser(PageRequest.of(page,size),idUser); // Sort.by(Sort.Direction.ASC, "basicTvShowInfo.originalName")
    }

    // Done
    // Get a page of the tv show reminder entity and then we build a PageResponseDTO based on the reminders.
    public PageResponseDTO getPageResponseDTO (Integer page, Integer size, Integer idUser) throws ResourceNotFoundException {
        Page<TvShowReminder> pageTvShowReminder = getPaginatedTvShowReminders(page,size,idUser);
        PageResponseDTO pageTvShowReminderDTO = buildPageTvShowReminderResponseDTO(pageTvShowReminder);

        return pageTvShowReminderDTO;
    }

    // Done
    // We will get a PageResponseDTO object with the information of the tv show reminders DTO in a list AND the information of the page.
    public PageResponseDTO buildPageTvShowReminderResponseDTO (Page pageTvShowReminder) throws ResourceNotFoundException {

        List<TvShowReminder> tvShowRemindersList = pageTvShowReminder.getContent();// Get all the tv show reminder of the user.
        List<TvShowReminderResponseDTO> tvShowReminderListDTO = new ArrayList<>();

        // If there is not content we return an empty PageResponseDTO
        if(tvShowRemindersList.size() == 0){
            return new PageResponseDTO(tvShowRemindersList, null);
        }

        for(TvShowReminder tvShowReminder: tvShowRemindersList) {

            BasicTvShowInfo basicTvShowInfo = tvShowReminder.getBasicTvShowInfo();
            TvShowDetailsResponseDTO tvShowDetailsResponseDTO = null;

            // Get the details of the show.
            if (basicTvShowInfo != null) {
                if (basicTvShowInfo.getId() != null) {
                    tvShowDetailsResponseDTO = tvShowDetailsService.getTvShowDetails(basicTvShowInfo.getId());
                }
            }
            // We Build the Tv Show Reminder DTO with all the information we manage to get so far.
            TvShowReminderResponseDTO tvShowReminderResponseDTO = new TvShowReminderResponseDTO();
            buildTvShowReminderDTO(tvShowReminderResponseDTO, tvShowReminder, tvShowDetailsResponseDTO);
            tvShowReminderListDTO.add(tvShowReminderResponseDTO);
        }

        // Build the PageResponseDTO object so we can return the list of tv show reminders DTO and description of the page.
        PageResponseDTO<TvShowReminderResponseDTO> pageTvShowReminderResponseDTO = new PageResponseDTO<>();
        buildPage(pageTvShowReminderResponseDTO, tvShowReminderListDTO, pageTvShowReminder);

        return pageTvShowReminderResponseDTO;
    }

    // Done
    // We build the PageResponseDTO object.
    private void buildPage(PageResponseDTO<TvShowReminderResponseDTO> page,
                           List<TvShowReminderResponseDTO> tvShowReminderListDTO,
                           Page pageTvShowReminder) {
        page.setItems(tvShowReminderListDTO);
        PageDescriptionDTO pageDescription = new PageDescriptionDTO();
        pageDescription.setTotalPages(pageTvShowReminder.getTotalPages()); // For the  number of total pages.
        pageDescription.setTotalElements(pageTvShowReminder.getTotalElements());// For total items stored in database.
        pageDescription.setNumberOfElementsReturn(pageTvShowReminder.getNumberOfElements());// Total elements we return on the page.
        page.setPageDescriptionDTO(pageDescription);
    }
}
