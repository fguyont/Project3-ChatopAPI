package com.openclassrooms.chatopapi.dto;

import lombok.Data;

@Data
public class RegisterRequest {
	private String name;
	private String email;
	private String password;
}
