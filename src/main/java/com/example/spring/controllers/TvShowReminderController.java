package com.example.spring.controllers;

import com.example.spring.models.TvShowReminder;
import com.example.spring.services.TvShowReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tvShowReminder")

public class TvShowReminderController {

    private final TvShowReminderService tvShowReminderService;

    @Autowired
    public TvShowReminderController(TvShowReminderService tvShowReminderService) {
        this.tvShowReminderService = tvShowReminderService;
    }

    @GetMapping("/{tvShowReminderId}")
    public Optional<TvShowReminder> getTvShowReminder(@PathVariable Integer idTvShowReminder){
        return tvShowReminderService.getTvShowReminder(idTvShowReminder);
    }

    @PostMapping("/")
    public void addTvShowReminder(@RequestBody TvShowReminder tvShowReminder) {
        tvShowReminderService.addTvShowReminder(tvShowReminder);
    }

    @GetMapping("/")
    public List<TvShowReminder> getAllTvShowsReminder(){
        return tvShowReminderService.getAllTvShowsReminder();
    }

    @DeleteMapping("/{idTvShowReminder}")
    public void deleteTvShowReminder(@PathVariable Integer idTvShowReminder){
        tvShowReminderService.deleteTvShowReminder(idTvShowReminder);
    }
}
