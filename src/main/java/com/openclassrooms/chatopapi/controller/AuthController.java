package com.openclassrooms.chatopapi.controller;

import lombok.AllArgsConstructor;
import com.openclassrooms.chatopapi.dto.AuthSuccess;
import com.openclassrooms.chatopapi.dto.LoginRequest;
import com.openclassrooms.chatopapi.dto.RegisterRequest;
import com.openclassrooms.chatopapi.dto.UserDto;
import com.openclassrooms.chatopapi.model.User;
import com.openclassrooms.chatopapi.service.AuthService;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private ModelMapper modelMapper;

	// Build Login REST API
	@PostMapping("/login")
	public ResponseEntity<AuthSuccess> authenticate(@RequestBody LoginRequest loginRequest) {
		String token = authService.login(loginRequest);

		AuthSuccess authSuccess = new AuthSuccess();
		authSuccess.setToken(token);

		return ResponseEntity.ok(authSuccess);
	}

	@PostMapping("/register")
	public ResponseEntity<AuthSuccess> register(@RequestBody RegisterRequest registerRequest) {

		String token = authService.register(registerRequest);

		AuthSuccess authSuccess = new AuthSuccess();
		authSuccess.setToken(token);

		return ResponseEntity.ok(authSuccess);
	}

	@GetMapping("/me")	
	public ResponseEntity<?> getMe() {
		Optional<User> user = authService.getMe();
		if (user == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		UserDto userDTO = modelMapper.map(user, UserDto.class);	
		return ResponseEntity.ok(userDTO);			
	}
}
