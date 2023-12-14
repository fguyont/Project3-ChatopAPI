package com.openclassrooms.chatopapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rentals")		
public class Rental {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Float surface;
	private Float price;
	private String picture;
	private String description;
	private Long owner_id;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	
	public Rental() {
		super();
	}

	
	public Rental(Long id, String name, Float surface, Float price, String description) {
		super();
		this.id = id;
		this.name = name;
		this.surface = surface;
		this.price = price;
		this.description = description;
	}
}
