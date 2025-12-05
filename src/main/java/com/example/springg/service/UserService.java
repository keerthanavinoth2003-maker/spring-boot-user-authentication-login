package com.example.springg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springg.model.UserModel;
import com.example.springg.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserModel register(UserModel user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);
    }

    public UserModel findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean matchesRawPassword(String raw, String encoded) {
        return encoder.matches(raw, encoded);
    }
}
