package com.example.spring.controllers;

import com.example.spring.models.UserTvShow;
import com.example.spring.services.UserTvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("/")
    public List<UserTvShow> getAllTvShowsCreatedByUser (){
        List<UserTvShow> userTvShows = userTvShowService.getAllTvShowsCreatedByUser();
        return userTvShows;
    }
}
