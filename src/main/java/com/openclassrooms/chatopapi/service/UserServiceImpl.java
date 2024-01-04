package com.openclassrooms.chatopapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.chatopapi.model.User;
import com.openclassrooms.chatopapi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> getUserById(Long id) {

        if (Double.isNaN(id) || id < 0) {
            throw new IllegalArgumentException("Invalid value for id");
        }

        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return user;
        }

        return null;
    }
}
