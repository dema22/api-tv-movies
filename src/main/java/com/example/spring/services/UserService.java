package com.example.spring.services;

import com.example.spring.dto.UserDTO;
import com.example.spring.exception.ResourceAlreadyExistsException;
import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.models.User;
import com.example.spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
// Done
@Service
public class UserService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(User user) throws ResourceAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if(userOptional.isPresent()){
            throw new ResourceAlreadyExistsException("Resource user already exists with username: " + user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getOneUser(Integer idUser) throws ResourceNotFoundException {
        return userRepository.findById(idUser).orElseThrow(() -> new ResourceNotFoundException("The user with the id : " + idUser + " was not found."));
    }

    public UserDTO getProfile(Integer idUser) {
        User user =  userRepository.findById(idUser).get();
        return createUserDTO(user);
    }

    private UserDTO createUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        return userDTO;
    }
}
