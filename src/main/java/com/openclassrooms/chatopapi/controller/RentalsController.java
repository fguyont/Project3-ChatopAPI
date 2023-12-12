package com.openclassrooms.chatopapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

	@GetMapping("")
	public String getResource() {
		return "a value...";
	}
}
