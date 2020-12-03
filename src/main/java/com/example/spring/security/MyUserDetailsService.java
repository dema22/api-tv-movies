package com.example.spring.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    // Now its hardcoded, but we need to get the user from our database with the help of a jpa repository.
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return new User("foo", "foo", new ArrayList<>());
    }
}
