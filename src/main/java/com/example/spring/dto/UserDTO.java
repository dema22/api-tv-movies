package com.example.spring.dto;

import com.example.spring.models.Role;

public class UserDTO {
    // attributes
    private String name;
    private String lastName;
    private String username;
    private String email;
    private Role role;

    // constructors
    public UserDTO(String name, String lastName, String username, String email, Role role) {
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public UserDTO() {
    }

    // G and s
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
