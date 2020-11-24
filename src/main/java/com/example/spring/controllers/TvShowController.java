package com.example.spring.controllers;

import com.example.spring.models.BasicTvShowInfo;
import com.example.spring.services.BasicTvShowInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tvShow")

public class TvShowController {
    private final BasicTvShowInfoService basicTvShowInfoService;

    @Autowired
    public TvShowController(BasicTvShowInfoService basicTvShowInfoService) {
        this.basicTvShowInfoService = basicTvShowInfoService;
    }

    @PostMapping("/loadTvShowTable")
    public void generateTvShowTable(@RequestBody List<BasicTvShowInfo> listBasicTvShowInfo) {
        try {
            basicTvShowInfoService.saveListOfBasicTvShows(listBasicTvShowInfo);
        }catch (OutOfMemoryError e){
        }
    }

    @GetMapping("/")
    public List<BasicTvShowInfo> searchBasicTvShowInfo (@RequestParam String originalNameTvShow){
        return basicTvShowInfoService.searchBasicTvShowInfo(originalNameTvShow);
    }
}
