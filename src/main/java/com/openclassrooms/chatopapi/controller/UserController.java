package com.openclassrooms.chatopapi.controller;

import java.util.Optional;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
	
	@Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "Gets user information")
	@ApiResponse(responseCode = "200", description = "User information loaded")
	@ApiResponse(responseCode = "404", description = "User not found")
	@ApiResponse(responseCode = "503", description = "Service unavailable")
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
		Optional<User> user;

		try {
			user = userService.getUserById(id);
		} catch (IllegalArgumentException ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
		UserDto userDto = new UserDto();

		if (user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		userDto = modelMapper.map(user, UserDto.class);
		return ResponseEntity.status(HttpStatus.OK).body(userDto);
	}
}
