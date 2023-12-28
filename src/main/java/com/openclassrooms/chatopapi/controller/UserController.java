package com.openclassrooms.chatopapi.controller;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.chatopapi.dto.UserDto;
import com.openclassrooms.chatopapi.model.User;
import com.openclassrooms.chatopapi.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Operation(summary = "Gets user information")
	@ApiResponse(responseCode = "200", description = "User information loaded")
	@ApiResponse(responseCode = "404", description = "User not found")
	@ApiResponse(responseCode = "503", description = "Service unavailable")
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		Optional<User> user;

		try {
			user = userService.getUserById(id);
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 error: Invalid id");
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("503 error: Service unavailable");
		}
		UserDto userDto = new UserDto();

		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 error: User not found");
		}

		userDto = modelMapper.map(user, UserDto.class);
		return ResponseEntity.ok(userDto);
	}
}
