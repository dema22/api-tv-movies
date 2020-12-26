package com.example.spring.controllers;

import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.models.BasicTvShowInfo;
import com.example.spring.services.BasicTvShowInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
//Done
@RestController
@RequestMapping("/tvShow")

public class TvShowController {
    private final BasicTvShowInfoService basicTvShowInfoService;

    @Autowired
    public TvShowController(BasicTvShowInfoService basicTvShowInfoService) {
        this.basicTvShowInfoService = basicTvShowInfoService;
    }

    // Admin endpoint , receives the json file of tv shows from "themoviedb" API.
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/file")
    public void loadTvShows(@RequestParam("file") MultipartFile file) throws IOException {
        basicTvShowInfoService.processTvShowFile(file);
    }

    // Public endpoint, anyone will query basic tv show information.
    @GetMapping("/")
    public ResponseEntity<List<BasicTvShowInfo>> searchBasicTvShowInfo (@RequestParam String originalNameTvShow){
        List<BasicTvShowInfo> basicTvShowInfoList =  basicTvShowInfoService.searchBasicTvShowInfo(originalNameTvShow);
        return (basicTvShowInfoList.size() > 0) ? ResponseEntity.ok(basicTvShowInfoList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Only the admin can search basic tv show info by ID.
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{idTvShow}")
    public ResponseEntity<BasicTvShowInfo> searchTvShowInfoById (@PathVariable Integer idTvShow) throws ResourceNotFoundException {
        return ResponseEntity.ok(basicTvShowInfoService.getBasicTvShowInfoById(idTvShow));
    }
}
