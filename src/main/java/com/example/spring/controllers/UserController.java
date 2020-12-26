package com.example.spring.controllers;

import com.example.spring.dto.AuthenticationRequestDTO;
import com.example.spring.dto.AuthenticationResponseDTO;
import com.example.spring.dto.UserDTO;
import com.example.spring.exception.ForbiddenActionExcepction;
import com.example.spring.exception.InvalidLoginException;
import com.example.spring.exception.ResourceAlreadyExistsException;
import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.models.User;
import com.example.spring.security.JwtTokenUtil;
import com.example.spring.services.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    // Done
    /*@ApiResponses(value = {
            @ApiResponse(code = 401, message = "Invalid username or password.")
    })*/
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDTO authenticationRequest) throws Exception {

        try {
            // We use the authentication manager of spring security to authenticate the AuthenticationRequestDTO that is coming in the request body.
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );

            final User user = (User) authenticate.getPrincipal();
            final String jwt = jwtTokenUtil.generateToken(user);

            return ResponseEntity.ok(new AuthenticationResponseDTO(jwt));
        }
        catch (BadCredentialsException e) {
            throw new InvalidLoginException("Invalid username or password.");
        }
    }

    // Done
    @PostMapping("/")
    public void addUser(@RequestBody @Valid User user) throws ResourceAlreadyExistsException {
        userService.addUser(user);
    }

    // Done
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/profile/me")
    public ResponseEntity<UserDTO> getProfile(@RequestHeader(name="Authorization") String header) {
        String token = jwtTokenUtil.getTokenFromAuthorizationHeader(header);
        Integer idLoggedUser = jwtTokenUtil.getUserId(token);
        return ResponseEntity.ok(userService.getProfile(idLoggedUser));
    }

    // Done
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users = userService.getAllUsers();
        return (users.size() > 0) ? ResponseEntity.ok(users) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Done
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{idUser}")
    public ResponseEntity<User> getUser(@PathVariable  Integer idUser) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.getOneUser(idUser));
    }
}
