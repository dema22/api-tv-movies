package com.example.spring.services;

import com.example.spring.dto.UserTvShowPatchDTO;
import com.example.spring.models.UserTvShow;
import com.example.spring.repositories.UserTvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTvShowService {

    private final UserTvShowRepository userTvShowRepository;

    @Autowired
    public UserTvShowService(UserTvShowRepository userTvShowRepository) {
        this.userTvShowRepository = userTvShowRepository;
    }

    public void addUserTvShow(UserTvShow userTvShow) {
        userTvShowRepository.save(userTvShow);
    }

    // We need to refactor this endpoint to get all the user tv shows by a particular user id.
    public List<UserTvShow> getAllTvShowsCreatedByUser() {
        return userTvShowRepository.findAll();
    }

    // Get a tv show created by the user by its ID.
    public Optional<UserTvShow> getUserTvShow(Integer idUserTvShow){
        return userTvShowRepository.findById(idUserTvShow);
    }

    // Update a tv show created by the user
    public void updateUserTvShow(UserTvShowPatchDTO userTvShowPatchDTO, Integer idUserTvShow) {
        Optional<UserTvShow> optionalUserTvShow = getUserTvShow(idUserTvShow);
        UserTvShow userTvShow = optionalUserTvShow.get();

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
