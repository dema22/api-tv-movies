package com.example.spring.controllers;

import com.example.spring.dto.UserTvShowDTO;
import com.example.spring.dto.UserTvShowPatchDTO;
import com.example.spring.exception.ResourceAlreadyExistsException;
import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.models.UserTvShow;
import com.example.spring.security.JwtTokenUtil;
import com.example.spring.services.UserTvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/userTvShow")

public class UserTvShowController {
    private final UserTvShowService userTvShowService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserTvShowController(UserTvShowService userTvShowService, JwtTokenUtil jwtTokenUtil) {
        this.userTvShowService = userTvShowService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/")
    public void addUserTvShow(@RequestBody UserTvShow userTvShow) throws ResourceAlreadyExistsException {
        userTvShowService.addUserTvShow(userTvShow);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/")
    public ResponseEntity<List<UserTvShowDTO>> getAllTvShowsCreatedByUser (@RequestHeader(name="Authorization") String header){
        String token = jwtTokenUtil.getTokenFromAuthorizationHeader(header);
        Integer idUser = jwtTokenUtil.getUserId(token);
        List<UserTvShowDTO> userTvShows = userTvShowService.getAllTvShowsCreatedByUser(idUser);
        return (userTvShows.size() > 0) ? ResponseEntity.ok(userTvShows) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/{idUserTvShow}")
    public void updateUserTvShow(@RequestHeader(name="Authorization") String header,@RequestBody @Valid UserTvShowPatchDTO userTvShowPatchDTO, @PathVariable  Integer idUserTvShow) throws ResourceNotFoundException {
        String token = jwtTokenUtil.getTokenFromAuthorizationHeader(header);
        Integer idUser = jwtTokenUtil.getUserId(token);
        userTvShowService.updateUserTvShow(idUser, userTvShowPatchDTO,idUserTvShow);
    }
}
