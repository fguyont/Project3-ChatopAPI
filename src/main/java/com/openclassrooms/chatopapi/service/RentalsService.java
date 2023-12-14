package com.openclassrooms.chatopapi.service;

import java.util.List;
import java.util.Optional;

import com.openclassrooms.chatopapi.model.Rental;

public interface RentalsService {
	List<Rental> getRentals();
	Optional<Rental> getRentalById(Long id);
	void createRental(Rental createdRental);
	void updateRental(Long id, Rental updatedRental);
}
