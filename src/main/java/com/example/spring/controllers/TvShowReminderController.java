package com.example.spring.controllers;

import com.example.spring.dto.TvShowReminderPatchDTO;
import com.example.spring.dto.TvShowReminderResponseDTO;
import com.example.spring.models.TvShowReminder;
import com.example.spring.services.TvShowReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tvShowReminder")

public class TvShowReminderController {

    private final TvShowReminderService tvShowReminderService;

    @Autowired
    public TvShowReminderController(TvShowReminderService tvShowReminderService) {
        this.tvShowReminderService = tvShowReminderService;
    }

    @GetMapping("/{idTvShowReminder}")
    public TvShowReminderResponseDTO getTvShowReminderResponseDTO(@PathVariable Integer idTvShowReminder){
        return tvShowReminderService.getTvShowReminderResponseDTO(idTvShowReminder);
    }

    @PostMapping("/")
    public void addTvShowReminder(@RequestBody TvShowReminder tvShowReminder) {
        tvShowReminderService.addTvShowReminder(tvShowReminder);
    }

    @GetMapping("/")
    public List<TvShowReminderResponseDTO> getAllTvShowsReminderDTO(){
        // Set a default a id user that will use to query all tv show reminder with that particular id.
        return tvShowReminderService.getAllTvShowsReminderDTO(1);
    }

    @DeleteMapping("/{idTvShowReminder}")
    public void deleteTvShowReminder(@PathVariable Integer idTvShowReminder){
        tvShowReminderService.deleteTvShowReminder(idTvShowReminder);
    }

    @PatchMapping("/{idTvShowReminder}")
    public void updateTvShowReminder(@RequestBody @Valid TvShowReminderPatchDTO tvShowReminderToUpdate, @PathVariable  Integer idTvShowReminder){
        tvShowReminderService.updateTvShowReminder(tvShowReminderToUpdate,idTvShowReminder);
    }
}
