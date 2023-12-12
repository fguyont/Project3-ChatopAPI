package com.openclassrooms.chatopapi.service;

import java.util.Optional;

import com.openclassrooms.chatopapi.dto.LoginRequest;
import com.openclassrooms.chatopapi.dto.RegisterRequest;
import com.openclassrooms.chatopapi.model.User;

public interface AuthService {
    String login(LoginRequest loginRequest);
    String register(RegisterRequest registerRequest);
    Optional<User> getMe();
}