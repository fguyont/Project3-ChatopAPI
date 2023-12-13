package com.openclassrooms.chatopapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

	@GetMapping("")
	@Operation(summary = "Gets all rentals")
	public String getResource() {
		return "a value...";
	}
}
