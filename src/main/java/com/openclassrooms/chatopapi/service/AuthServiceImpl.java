package com.openclassrooms.chatopapi.service;

import com.openclassrooms.chatopapi.dto.LoginRequest;
import com.openclassrooms.chatopapi.dto.RegisterRequest;
import com.openclassrooms.chatopapi.model.User;
import com.openclassrooms.chatopapi.repository.UserRepository;
import com.openclassrooms.chatopapi.security.JwtTokenProvider;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private ModelMapper modelMapper;

	public AuthServiceImpl(JwtTokenProvider jwtTokenProvider, UserRepository userRepository,
			PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public String login(LoginRequest loginRequest) {

		if (!loginRequest.getEmail().contains("@") || loginRequest.getPassword().length() < 3) {
			throw new IllegalArgumentException("Invalid input");
		}

		if (userRepository.existsByEmail(loginRequest.getEmail()) == false) {
			throw new IllegalArgumentException("User email not found");
		}
		return authenticate(loginRequest);
	}

	@Override
	public String register(RegisterRequest registerRequest) {

		if (registerRequest.getName().length() < 3 || !registerRequest.getEmail().contains("@")
				|| registerRequest.getPassword().length() < 3) {
			throw new IllegalArgumentException("Invalid input");
		}

		if (userRepository.existsByEmail(registerRequest.getEmail()) == true) {
			throw new IllegalArgumentException("Email already exists");
		}

		User user = modelMapper.map(registerRequest, User.class);
		user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
		user.setCreated_at(LocalDateTime.now());
		user.setUpdated_at(LocalDateTime.now());

		userRepository.save(user);

		LoginRequest loginRequest = new LoginRequest(registerRequest.getEmail(), registerRequest.getPassword());
		return authenticate(loginRequest);
	}

	@Override
	public Optional<User> getMe() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();

		Optional<User> user = userRepository.findByEmail(name);

		if (user.isPresent()) {
			return user;
		}
		return null;
	}

	private String authenticate(LoginRequest loginRequest) {
		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		} catch (AuthenticationCredentialsNotFoundException ex) {
			throw new AuthenticationCredentialsNotFoundException("Authentication not permitted");
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);
		return token;
	}
}
