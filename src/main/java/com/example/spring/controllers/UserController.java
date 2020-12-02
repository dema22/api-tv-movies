package com.example.spring.controllers;

import com.example.spring.dto.AuthenticationRequestDTO;
import com.example.spring.dto.AuthenticationResponseDTO;
import com.example.spring.models.User;
import com.example.spring.security.MyUserDetailsService;
import com.example.spring.services.UserService;
import com.example.spring.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")

public class UserController {
    private AuthenticationManager authenticationManager;
    private MyUserDetailsService userDetailsService;
    private JwtUtil jwtTokenUtil;
    private final UserService userService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService, JwtUtil jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }
    
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDTO authenticationRequest) throws Exception {

        try {
            // Authentication manager of spring security to authenticate the AuthenticationRequestDTO that is coming in the request body.
            // TO DO : authenticate against the database later.
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        // Then i fetch the user details so i can create the jwt and i return it to the client.
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponseDTO(jwt));
    }


    @PostMapping("/")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @GetMapping("/")
    public List<User> getAllUser(){
        List<User> users = userService.getAllUsers();
        return users;
    }
}
