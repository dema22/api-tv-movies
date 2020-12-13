package com.example.spring.controllers;

import com.example.spring.dto.PageTvShowRemindersResponseDTO;
import com.example.spring.dto.TvShowReminderPatchDTO;
import com.example.spring.dto.TvShowReminderResponseDTO;
import com.example.spring.exception.BusinessLogicValidationFailure;
import com.example.spring.exception.ForbiddenActionExcepction;
import com.example.spring.exception.ResourceAlreadyExistsException;
import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.models.TvShowReminder;
import com.example.spring.services.TvShowReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public TvShowReminderResponseDTO getTvShowReminderResponseDTO(@PathVariable Integer idTvShowReminder) throws ResourceNotFoundException {
        return tvShowReminderService.getTvShowReminderResponseDTO(idTvShowReminder);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/")
    public void addTvShowReminder(@RequestBody @Valid TvShowReminder tvShowReminder) throws BusinessLogicValidationFailure, ResourceAlreadyExistsException {
        tvShowReminderService.addTvShowReminder(tvShowReminder);
    }

    @GetMapping("/")
    public List<TvShowReminderResponseDTO> getAllTvShowsReminderDTO() throws ResourceNotFoundException {
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

    // Pagination
    // Add a new method to return PageTvShowRemindersResponseDTO.
    // This object has a list of tv show reminders response dto and also has the pageDTO information.
    // Request parameters default in true -> mandatory.
    @GetMapping("/paginated")
    public PageTvShowRemindersResponseDTO getPaginatedTvShowReminderResponseDTO(@RequestParam Integer page, @RequestParam Integer size) throws ResourceNotFoundException {
        // Set a default a id user that will use to query all tv show reminder with that particular id.
        // We will get the user info from the jwt token when we implement Spring Security on the Api.
        return tvShowReminderService.getPaginatedTvShowReminderResponseDTO(page,size,1);
    }
}
