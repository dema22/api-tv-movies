package com.example.spring.services;

import com.example.spring.models.TvShow;
import com.example.spring.repositories.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TvShowService {

    private final TvShowRepository tvShowRepository;

    @Autowired
    public TvShowService(TvShowRepository tvShowRepository) {
        this.tvShowRepository = tvShowRepository;
    }

    public void addTvShow(TvShow tvShow) {
        tvShowRepository.save(tvShow);
    }

    public List<TvShow> getAllTvShows() {
        return tvShowRepository.findAll();
    }

    public void deleteTvShow(Integer idTvShow) {
        tvShowRepository.deleteById(idTvShow);
    }
}
