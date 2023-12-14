package com.openclassrooms.chatopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.chatopapi.model.Rental;

public interface RentalRepository extends JpaRepository<Rental,Long>{

}
