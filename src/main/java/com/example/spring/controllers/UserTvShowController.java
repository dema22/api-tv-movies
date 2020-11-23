package com.example.spring.controllers;

import com.example.spring.dto.UserTvShowPatchDTO;
import com.example.spring.models.UserTvShow;
import com.example.spring.services.UserTvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/userTvShow")

public class UserTvShowController {
    private final UserTvShowService userTvShowService;

    @Autowired
    public UserTvShowController(UserTvShowService userTvShowService) {
        this.userTvShowService = userTvShowService;
    }

    @PostMapping("/")
    public void addUserTvShow(@RequestBody UserTvShow userTvShow) {
        userTvShowService.addUserTvShow(userTvShow);
    }

    // This endpoint is returning all tv shows in the database we need to query them by the user.
    @GetMapping("/")
    public List<UserTvShow> getAllTvShowsCreatedByUser (){
        List<UserTvShow> userTvShows = userTvShowService.getAllTvShowsCreatedByUser();
        return userTvShows;
    }

    @GetMapping("/{idUserTvShow}")
    public Optional<UserTvShow> getUserTvShow (@PathVariable Integer idUserTvShow){
        return userTvShowService.getUserTvShow(idUserTvShow);
    }

    @PatchMapping("/{idUserTvShow}")
    public void updateUserTvShow(@Valid @RequestBody UserTvShowPatchDTO userTvShowPatchDTO, @PathVariable  Integer idUserTvShow){
        userTvShowService.updateUserTvShow(userTvShowPatchDTO,idUserTvShow);
    }
}
