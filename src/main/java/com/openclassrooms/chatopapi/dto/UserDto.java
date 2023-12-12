package com.openclassrooms.chatopapi.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDto {
	private Long id;
	private String email;
	private String name;
	private String password;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
}
