package com.example.spring.services;

import com.example.spring.dto.UserTvShowDTO;
import com.example.spring.dto.UserTvShowPatchDTO;
import com.example.spring.exception.BusinessLogicValidationFailure;
import com.example.spring.exception.ResourceAlreadyExistsException;
import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.models.UserTvShow;
import com.example.spring.repositories.UserTvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// Done
@Service
public class UserTvShowService {

    private final UserTvShowRepository userTvShowRepository;

    @Autowired
    public UserTvShowService(UserTvShowRepository userTvShowRepository) {
        this.userTvShowRepository = userTvShowRepository;
    }

    public UserTvShowDTO addUserTvShow(Integer idLoggedUser, UserTvShow userTvShow) throws ResourceAlreadyExistsException, BusinessLogicValidationFailure {

        Optional<UserTvShow> searchDuplicationUserTvShowOptional;
        Optional<UserTvShow> searchTheSavedUserTvShow;
        UserTvShowDTO userTvShowDTO = new UserTvShowDTO();

        if(idLoggedUser != userTvShow.getUser().getIdUser()){
            throw new BusinessLogicValidationFailure("The current logged user CANT add a tv show reminder to another user account.");
        }

        searchDuplicationUserTvShowOptional = userTvShowRepository.findByNameTvShowAndUserId(userTvShow.getNameTvShow(), userTvShow.getUser().getIdUser());

        if(searchDuplicationUserTvShowOptional.isPresent()){
            throw new ResourceAlreadyExistsException("User already created a tv show with the name:  " + userTvShow.getNameTvShow());
        }

        // Saved the user tv show.
        userTvShowRepository.save(userTvShow);

        // We look for the user tv show we just saved, we build its DTO and return it.
        searchTheSavedUserTvShow = userTvShowRepository.findByNameTvShowAndUserId(userTvShow.getNameTvShow(), userTvShow.getUser().getIdUser());
        buildUserTvShowDTO(searchTheSavedUserTvShow.get(), userTvShowDTO);

        return userTvShowDTO;
    }

    public List<UserTvShowDTO> getAllTvShowsCreatedByUser(Integer idUser) {
        List<UserTvShow> userTvShowList = userTvShowRepository.findByUser_IdUser(idUser);
        List<UserTvShowDTO> userTvShowDTOList = new ArrayList<>();

        for(UserTvShow userTvShow: userTvShowList){
            UserTvShowDTO userTvShowDTO = new UserTvShowDTO();
            buildUserTvShowDTO(userTvShow, userTvShowDTO);
            userTvShowDTOList.add(userTvShowDTO);
        }
        return userTvShowDTOList;
    }

    public void buildUserTvShowDTO(UserTvShow userTvShow, UserTvShowDTO userTvShowDTO){
        userTvShowDTO.setIdTvShowCreatedByUser(userTvShow.getIdTvShowCreatedByUser());
        userTvShowDTO.setNameTvShow(userTvShow.getNameTvShow());
        userTvShowDTO.setGenre(userTvShow.getGenre());
        userTvShowDTO.setProductionCompany(userTvShow.getProductionCompany());
    }

    public UserTvShow getUserTvShow(Integer idUser, Integer idUserTvShow) throws ResourceNotFoundException {
        return userTvShowRepository.findByIdTvShowAndUserId(idUserTvShow, idUser).orElseThrow(() -> new ResourceNotFoundException("The user tv show with the id : " + idUserTvShow + " was not found."));
    }

    // Update a tv show created by the user ( We first check if the user tv show we want to update belongs to the logged user).
    public void updateUserTvShow(Integer idUser, UserTvShowPatchDTO userTvShowPatchDTO, Integer idUserTvShow) throws ResourceNotFoundException {
        UserTvShow userTvShow = getUserTvShow(idUser, idUserTvShow);

        // I Assign the information of the patch DTO to our entity and save it.
        // I only update if jsoNullable attributes from the UserTvShowPatchDTO has values if omitted we dont update.

        if (userTvShowPatchDTO.getNameTvShow().isPresent()) {
            userTvShow.setNameTvShow(userTvShowPatchDTO.getNameTvShow().get());
        }

        if (userTvShowPatchDTO.getGenre().isPresent()) {
            userTvShow.setGenre(userTvShowPatchDTO.getGenre().get());
        }

        if (userTvShowPatchDTO.getProductionCompany().isPresent()) {
            userTvShow.setProductionCompany(userTvShowPatchDTO.getProductionCompany().get());
        }

        userTvShowRepository.save(userTvShow);
    }

    // First we check if the user tv show belongs to the logged user before we delete it.
    public void deleteUserTvShow(Integer idUser, Integer idUserTvShow) throws ResourceNotFoundException {
        if(getUserTvShow(idUser,idUserTvShow) != null){
            userTvShowRepository.deleteById(idUserTvShow);
        }
    }
}
