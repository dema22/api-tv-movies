package com.example.spring.controllers;

import com.example.spring.dto.PageTvShowRemindersResponseDTO;
import com.example.spring.dto.TvShowReminderPatchDTO;
import com.example.spring.dto.TvShowReminderResponseDTO;
import com.example.spring.exception.BusinessLogicValidationFailure;
import com.example.spring.exception.ForbiddenActionExcepction;
import com.example.spring.exception.ResourceAlreadyExistsException;
import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.models.TvShowReminder;
import com.example.spring.security.JwtTokenUtil;
import com.example.spring.services.TvShowReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tvShowReminder")

public class TvShowReminderController {

    private final TvShowReminderService tvShowReminderService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public TvShowReminderController(TvShowReminderService tvShowReminderService, JwtTokenUtil jwtTokenUtil) {
        this.tvShowReminderService = tvShowReminderService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    // Review
    @GetMapping("/{idTvShowReminder}")
    public TvShowReminderResponseDTO getTvShowReminderResponseDTO(@PathVariable Integer idTvShowReminder) throws ResourceNotFoundException {
        return tvShowReminderService.getTvShowReminderResponseDTO(idTvShowReminder);
    }

    // Done
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/")
    public void addTvShowReminder(@RequestHeader(name="Authorization") String header,
                                  @RequestBody @Valid TvShowReminder tvShowReminder) throws BusinessLogicValidationFailure, ResourceAlreadyExistsException, ResourceNotFoundException {
        String token = jwtTokenUtil.getTokenFromAuthorizationHeader(header);
        Integer idLoggedUser = jwtTokenUtil.getUserId(token);
        if(idLoggedUser != tvShowReminder.getUser().getIdUser()){
            throw new BusinessLogicValidationFailure("The current logged user CANT add a tv show reminder to another user account.");
        }
        tvShowReminderService.addTvShowReminder(tvShowReminder);
    }

    // Done
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/")
    public ResponseEntity<List<TvShowReminderResponseDTO>> getAllTvShowsReminderDTO(@RequestHeader(name="Authorization") String header) throws ResourceNotFoundException {
        String token = jwtTokenUtil.getTokenFromAuthorizationHeader(header);
        Integer idUser = jwtTokenUtil.getUserId(token);
        List<TvShowReminderResponseDTO> tvShowReminderResponseDTOList = tvShowReminderService.getAllTvShowsReminderDTO(idUser);
        return (tvShowReminderResponseDTOList.size() > 0) ? ResponseEntity.ok(tvShowReminderResponseDTOList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Review
    @DeleteMapping("/{idTvShowReminder}")
    public void deleteTvShowReminder(@PathVariable Integer idTvShowReminder){
        tvShowReminderService.deleteTvShowReminder(idTvShowReminder);
    }

    // Done
    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/{idTvShowReminder}")
    public void updateTvShowReminder(@RequestHeader(name="Authorization") String header,
                                     @RequestBody @Valid TvShowReminderPatchDTO tvShowReminderToUpdate,
                                     @PathVariable  Integer idTvShowReminder) throws ResourceNotFoundException {
        String token = jwtTokenUtil.getTokenFromAuthorizationHeader(header);
        Integer idUser = jwtTokenUtil.getUserId(token);
        tvShowReminderService.updateTvShowReminder(idUser, tvShowReminderToUpdate,idTvShowReminder);
    }

    // Done
    // Pagination
    // Add a new method to return PageTvShowRemindersResponseDTO.
    // This object has a list of tv show reminders response dto and also has the pageDTO information.
    // Request parameters default in true -> mandatory.
    // Note: a pageable object starts in index 0.
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/paginated")
    public ResponseEntity<PageTvShowRemindersResponseDTO> getPaginatedTvShowReminderResponseDTO(@RequestHeader(name="Authorization") String header,
                                                                                @RequestParam Integer page,
                                                                                @RequestParam Integer size) throws ResourceNotFoundException {
        String token = jwtTokenUtil.getTokenFromAuthorizationHeader(header);
        Integer idUser = jwtTokenUtil.getUserId(token);
        PageTvShowRemindersResponseDTO pageTvShowRemindersResponseDTO = tvShowReminderService.getPaginatedTvShowReminderResponseDTO(page,size,idUser);
        return (pageTvShowRemindersResponseDTO.getTvShowRemindersResponseDTO().size() > 0) ? ResponseEntity.ok(pageTvShowRemindersResponseDTO) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
