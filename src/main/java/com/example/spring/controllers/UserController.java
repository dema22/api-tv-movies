package com.example.spring.controllers;

import com.example.spring.models.User;
import com.example.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")

public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
