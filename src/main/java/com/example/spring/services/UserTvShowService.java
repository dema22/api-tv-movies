package com.example.spring.services;

import com.example.spring.models.UserTvShow;
import com.example.spring.repositories.UserTvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<UserTvShow> getAllTvShowsCreatedByUser() {
        return userTvShowRepository.findAll();
    }
}
