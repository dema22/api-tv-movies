package com.example.spring.services;

import com.example.spring.dto.UserTvShowDTO;
import com.example.spring.dto.UserTvShowPatchDTO;
import com.example.spring.exception.ResourceAlreadyExistsException;
import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.models.UserTvShow;
import com.example.spring.repositories.UserTvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserTvShowService {

    private final UserTvShowRepository userTvShowRepository;

    @Autowired
    public UserTvShowService(UserTvShowRepository userTvShowRepository) {
        this.userTvShowRepository = userTvShowRepository;
    }

    public void addUserTvShow(UserTvShow userTvShow) throws ResourceAlreadyExistsException {
        Optional<UserTvShow> userTvShowOptional = userTvShowRepository.findByNameTvShowAndUserId(userTvShow.getNameTvShow(), userTvShow.getUser().getIdUser());
        if(userTvShowOptional.isPresent()){
            throw new ResourceAlreadyExistsException("User already created a tv show with the name:  " + userTvShow.getNameTvShow());
        }
        userTvShowRepository.save(userTvShow);
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

    // Update a tv show created by the user
    public void updateUserTvShow(Integer idUser, UserTvShowPatchDTO userTvShowPatchDTO, Integer idUserTvShow) throws ResourceNotFoundException {
        UserTvShow userTvShow = getUserTvShow(idUser, idUserTvShow);

        // Assign the information of the DTO to our entity and save it.
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
}
