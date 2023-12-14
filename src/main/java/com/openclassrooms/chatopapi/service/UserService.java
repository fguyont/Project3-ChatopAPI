package com.openclassrooms.chatopapi.service;

import java.util.Optional;

import com.openclassrooms.chatopapi.model.User;

public interface UserService {
	Optional<User> getUserById(Long id);
}
