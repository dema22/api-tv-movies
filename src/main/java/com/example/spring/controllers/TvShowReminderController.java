package com.example.spring.controllers;

import com.example.spring.dto.PageInfoDTO;
import com.example.spring.dto.PageResponseDTO;
import com.example.spring.dto.TvShowReminderPatchDTO;
import com.example.spring.dto.TvShowReminderResponseDTO;
import com.example.spring.exception.BusinessLogicValidationFailure;
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
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/")
    public ResponseEntity<TvShowReminderResponseDTO> addTvShowReminder(@RequestHeader(name="Authorization") String header,
                                                                       @RequestBody @Valid TvShowReminder tvShowReminder) throws BusinessLogicValidationFailure, ResourceAlreadyExistsException, ResourceNotFoundException {
        String token = jwtTokenUtil.getTokenFromAuthorizationHeader(header);
        Integer idLoggedUser = jwtTokenUtil.getUserId(token);
        return ResponseEntity.ok(tvShowReminderService.addTvShowReminder(idLoggedUser,tvShowReminder));
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

    // Done
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{idTvShowReminder}")
    public ResponseEntity<List<TvShowReminder>> deleteTvShowReminder(@RequestHeader(name="Authorization") String header,
                                     @PathVariable Integer idTvShowReminder,
                                     @RequestBody(required = false) @Valid PageInfoDTO pageInfo) throws ResourceNotFoundException {
        String token = jwtTokenUtil.getTokenFromAuthorizationHeader(header);
        Integer idUser = jwtTokenUtil.getUserId(token);
        PageResponseDTO pageTvShowReminder = tvShowReminderService.deleteTvShowReminder(idUser, idTvShowReminder,pageInfo);
        return (pageTvShowReminder.getItems().size() > 0) ? ResponseEntity.ok(pageTvShowReminder.getItems()) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Done
    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/{idTvShowReminder}")
    public ResponseEntity<TvShowReminderResponseDTO> updateTvShowReminder(@RequestHeader(name="Authorization") String header,
                                                                          @RequestBody @Valid TvShowReminderPatchDTO tvShowReminderToUpdate,
                                                                          @PathVariable  Integer idTvShowReminder) throws ResourceNotFoundException {
        String token = jwtTokenUtil.getTokenFromAuthorizationHeader(header);
        Integer idUser = jwtTokenUtil.getUserId(token);
        return ResponseEntity.ok(tvShowReminderService.updateTvShowReminder(idUser, tvShowReminderToUpdate,idTvShowReminder));
    }

    // Done
    // This endpoint returns a paginated result of tv shows reminders of the logged user.
    // Request parameters default in true -> mandatory. To remember : a pageable object starts in index 0.
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/paginated")
    public ResponseEntity<PageResponseDTO> getPageResponseDTO(@RequestHeader(name="Authorization") String header,
                                                                                 @RequestParam Integer page,
                                                                                 @RequestParam Integer size) throws ResourceNotFoundException {
        String token = jwtTokenUtil.getTokenFromAuthorizationHeader(header);
        Integer idUser = jwtTokenUtil.getUserId(token);
        PageResponseDTO pageTvShowRemindersResponseDTO = tvShowReminderService.getPageResponseDTO(page,size,idUser);
        return (pageTvShowRemindersResponseDTO.getItems().size() > 0) ? ResponseEntity.ok(pageTvShowRemindersResponseDTO) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
