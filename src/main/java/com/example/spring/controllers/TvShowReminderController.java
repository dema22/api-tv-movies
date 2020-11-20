package com.example.spring.controllers;

import com.example.spring.dto.TvShowReminderResponseDTO;
import com.example.spring.models.TvShowReminder;
import com.example.spring.services.TvShowReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tvShowReminder")

public class TvShowReminderController {

    private final TvShowReminderService tvShowReminderService;

    @Autowired
    public TvShowReminderController(TvShowReminderService tvShowReminderService) {
        this.tvShowReminderService = tvShowReminderService;
    }

    /*// MODIFICO EL METODO TEMPORALMENTE PARA DEOLVER UN TV SHOW DETAILS EN VEZ DE UN TV SHOW REMINDER
    @GetMapping("/{idTvShowReminder}")
    public Optional<TvShowDetails> getTvShowReminder(@PathVariable Integer idTvShowReminder){
        return tvShowReminderService.getTvShowReminder(idTvShowReminder);
    }*/



    @GetMapping("/{idTvShowReminder}")
    public TvShowReminderResponseDTO getTvShowReminderResponseDTO(@PathVariable Integer idTvShowReminder){
        return tvShowReminderService.getTvShowReminderResponseDTO(idTvShowReminder);
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
