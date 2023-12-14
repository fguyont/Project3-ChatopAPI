package com.openclassrooms.chatopapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import com.openclassrooms.chatopapi.dto.RentalDto;
import com.openclassrooms.chatopapi.dto.RentalsResponse;
import com.openclassrooms.chatopapi.dto.RentalResponse;
import com.openclassrooms.chatopapi.model.Rental;
import com.openclassrooms.chatopapi.model.User;
import com.openclassrooms.chatopapi.service.AuthService;
import com.openclassrooms.chatopapi.service.FileStorageService;
import com.openclassrooms.chatopapi.service.RentalsService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

	@Autowired
	private RentalsService rentalsService;
	
	@Autowired
	private AuthService authService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private FileStorageService fileStorageService;

	@GetMapping("")
	@Operation(summary = "Gets all rentals")
	public ResponseEntity<?> getRentals() {

		List<Rental> rentals;
		try {
			rentals = rentalsService.getRentals();
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("503 error: Service unavailable");
		}
		RentalsResponse rentalsResponse = new RentalsResponse();
		List<RentalDto> rentalsDto = new ArrayList<RentalDto>();

		for (Rental r : rentals) {
			RentalDto rentalDto = modelMapper.map(r, RentalDto.class);
			rentalsDto.add(rentalDto);
		}
		rentalsResponse.setRentals(rentalsDto);

		return ResponseEntity.ok(rentalsResponse);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getRentalById(@PathVariable Long id) {
		Optional<Rental> rental;

		try {
			rental = rentalsService.getRentalById(id);
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 error: Invalid id");
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("503 error: Service unavailable");
		}
		RentalDto rentalDto = new RentalDto();

		if (rental == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 error: User not found");
		}

		rentalDto = modelMapper.map(rental, RentalDto.class);
		return ResponseEntity.ok(rentalDto);
	}

	@PostMapping("")
	public ResponseEntity<?> createRental(@RequestParam("name") String name, @RequestParam("surface") float surface,
			@RequestParam("price") float price, @RequestParam MultipartFile picture,
			@RequestParam("description") String description) {

		String pictureUrl = fileStorageService.storeFile(picture);

		Rental createdRental = new Rental();
		createdRental.setName(name);
		createdRental.setSurface(surface);
		createdRental.setPrice(price);
		createdRental.setPicture(pictureUrl);
		createdRental.setDescription(description);
		User me = authService.getMe().orElse(null);
		createdRental.setOwner_id(me.getId());
		rentalsService.createRental(createdRental);
		
		RentalResponse rentalResponse = new RentalResponse();
		rentalResponse.setMessage("Rental created");

		return ResponseEntity.ok(rentalResponse);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateRental(@PathVariable Long id, @RequestParam("name") String name,
			@RequestParam("surface") float surface, @RequestParam("price") float price,
			@RequestParam("description") String description) {
		Rental updatedRental = new Rental(id, name, surface, price, description);
		try {
			rentalsService.updateRental(id, updatedRental);
		} catch (NotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 error: Rental not found");

		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 error: Invalid input for update");

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("503 error: Service unavailable");
		}

		RentalResponse rentalResponse = new RentalResponse();
		rentalResponse.setMessage("Rental updated!");
		return ResponseEntity.ok(rentalResponse);
	}
}
