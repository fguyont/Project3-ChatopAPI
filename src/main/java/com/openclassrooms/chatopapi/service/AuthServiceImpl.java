package com.openclassrooms.chatopapi.service;

import com.openclassrooms.chatopapi.dto.LoginRequest;
import com.openclassrooms.chatopapi.dto.RegisterRequest;
import com.openclassrooms.chatopapi.model.User;
import com.openclassrooms.chatopapi.repository.UserRepository;
import com.openclassrooms.chatopapi.security.JwtTokenProvider;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private ModelMapper modelMapper;


    public AuthServiceImpl(
            JwtTokenProvider jwtTokenProvider,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Override
    public String login(LoginRequest loginRequest) {
    	

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
    
    @Override
    public String register(RegisterRequest registerRequest) {
    	
    	User user = modelMapper.map(registerRequest, User.class);
    	user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
    	user.setCreated_at(LocalDateTime.now());
    	user.setUpdated_at(LocalDateTime.now());
    	
    	userRepository.save(user);
    	
    	Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
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
}
