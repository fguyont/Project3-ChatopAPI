package com.openclassrooms.chatopapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.openclassrooms.chatopapi.model.Rental;
import com.openclassrooms.chatopapi.repository.RentalRepository;

@Service
public class RentalsServiceImpl implements RentalsService {

	@Autowired
	private RentalRepository rentalRepository;

	@Override
	public List<Rental> getRentals() {
		return rentalRepository.findAll();	
	}
	
	@Override
	public Optional<Rental> getRentalById(Long id) {
		
		if (Double.isNaN(id) || id<0) {
			throw new IllegalArgumentException("Invalid value for id");
		}
		
		Optional<Rental> rental = rentalRepository.findById(id);
		
		if (rental.isPresent()) {
			return rental;
		}
		
		return null;
	}
	
	@Override
	public void createRental(Rental createdRental) {
		rentalRepository.save(createdRental);
	}
	
	@Override
	public void updateRental(Long id, Rental updatedRental) {
		
		if (updatedRental.getName().isBlank() || updatedRental.getSurface().isNaN() || updatedRental.getPrice().isNaN() || updatedRental.getDescription().isBlank()) {
			throw new IllegalArgumentException("Invalid input for update!");
		}
		
		Rental rentalToUpdate = rentalRepository.findById(id).orElse(null);
		
		if (rentalToUpdate == null) {
			throw new NotFoundException("Rental not found!");
		}
		
		if (rentalToUpdate.getName() != updatedRental.getName()) {
			rentalToUpdate.setName(updatedRental.getName());
		}
		if (rentalToUpdate.getSurface() != updatedRental.getSurface()) {
			rentalToUpdate.setSurface(updatedRental.getSurface());
		}
		if (rentalToUpdate.getPrice() != updatedRental.getPrice()) {
			rentalToUpdate.setPrice(updatedRental.getPrice());
		}
		if (rentalToUpdate.getDescription() != updatedRental.getDescription()) {
			rentalToUpdate.setDescription(updatedRental.getDescription());
		}
		
		rentalToUpdate.setUpdated_at(LocalDateTime.now());
		
		rentalRepository.save(rentalToUpdate);
	}
}
