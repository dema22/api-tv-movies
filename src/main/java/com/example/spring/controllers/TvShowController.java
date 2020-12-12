package com.example.spring.controllers;

import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.models.BasicTvShowInfo;
import com.example.spring.services.BasicTvShowInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // Only the admin can load the file from themoviedb API with the tv shows information.
    @PostMapping("/loadTvShowTable")
    public void generateTvShowTable(@RequestBody List<BasicTvShowInfo> listBasicTvShowInfo) {
        try {
            basicTvShowInfoService.saveListOfBasicTvShows(listBasicTvShowInfo);
        }catch (OutOfMemoryError e){
        }
    }

    // Public endpoint, anyone will query basic tv show information.
    @GetMapping("/")
    public ResponseEntity<List<BasicTvShowInfo>> searchBasicTvShowInfo (@RequestParam String originalNameTvShow){
        List<BasicTvShowInfo> basicTvShowInfoList =  basicTvShowInfoService.searchBasicTvShowInfo(originalNameTvShow);
        return (basicTvShowInfoList.size() > 0) ? ResponseEntity.ok(basicTvShowInfoList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{idTvShow}")
    public ResponseEntity<BasicTvShowInfo> searchTvShowInfoById (@PathVariable Integer idTvShow) throws ResourceNotFoundException {
        return ResponseEntity.ok(basicTvShowInfoService.getBasicTvShowInfoById(idTvShow));
    }

    // Is it better to Include a endpoint "/{idTvShow}/tvShowDetails" instead of having a controller of tv show details ?
}
